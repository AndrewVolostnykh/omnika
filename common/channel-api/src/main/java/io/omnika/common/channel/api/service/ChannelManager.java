package io.omnika.common.channel.api.service;

import io.omnika.common.ipc.config.Topics;
import io.omnika.common.ipc.dto.ChannelsRequest;
import io.omnika.common.ipc.dto.OutboundChannelMessage;
import io.omnika.common.ipc.events.ChannelEntityEvent;
import io.omnika.common.ipc.events.EntityEventType;
import io.omnika.common.ipc.events.InstanceCountChangedEvent;
import io.omnika.common.ipc.service.PartitionService;
import io.omnika.common.ipc.service.QueueService;
import io.omnika.common.model.channel.ServiceType;
import io.omnika.common.model.channel.ChannelConfig;
import io.omnika.common.model.channel.ChannelType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelManager {

    private final ChannelFactory channelFactory;
    private final QueueService queueService;
    private final PartitionService partitionService;

    private final Map<UUID, ChannelService<?>> channels = new ConcurrentHashMap<>();
    // TODO: handle channels events and repartition channels when instance count changes
    // TODO: change logging to entity events

    @PostConstruct
    public void init() {
        queueService.subscribe(Topics.channelEvents(getChannelType()), this::handleChannelEvent);
        queueService.subscribe(Topics.outboundChannelMessages(getChannelType()), this::handleOutboundChannelMessage);
    }

    @EventListener(value = InstanceCountChangedEvent.class, condition = "#event.serviceType.toString() == 'TELEGRAM_CHANNEL'")
    public void rebalance(InstanceCountChangedEvent event) {
        log.info("Got {}", event);
        Set<UUID> toRemove = new HashSet<>();
        channels.forEach((channelId, channelService) -> {
            if (!partitionService.isMyPartition(channelId)) {
                log.info("Channel {} is not at assigned partition, stopping", channelId);
                stop(channelService); // TODO: do in parallel
                toRemove.add(channelId);
            }
        });
        toRemove.forEach(channels::remove);

        requestChannels();
    }

    private void requestChannels() {
        ChannelsRequest channelsRequest = ChannelsRequest.builder()
                .channelType(getChannelType())
                .build();
        queueService.sendMessage(ServiceType.MANAGEMENT_SERVICE, Topics.getChannels(), channelsRequest,
                Topics.channelEvents(getChannelType()));
        log.info("Requested channel list");
    }

    private void handleOutboundChannelMessage(OutboundChannelMessage message) {
        ChannelService<?> channelService = channels.get(message.getChannelId());
        if (channelService == null) {
            log.error("Cannot publish message to channel {} because it is not initialized", message.getChannelId());
            return;
        }
        try {
            channelService.sendMessage(message);
        } catch (Exception e) {
            log.error("Failed to send message to channel {}", message.getChannelId(), e);
        }
    }

    private void handleChannelEvent(ChannelEntityEvent event) {
        log.info("Handling channel event: {}", event);
        UUID channelId = event.getChannelId();
        ChannelConfig config = event.getChannelConfig();

        if (!partitionService.isMyPartition(channelId)) {
            log.info("Channel {} is not at assigned partition", channelId);
            return;
        }

        ChannelService<?> channelService = channels.get(channelId);
        if (channelService != null && event.getEventType() == EntityEventType.DELETED) {
            stop(channelService);
            return;
        }
        if (channelService != null) {
            if (!channelService.getConfig().equals(config)) {
                stop(channelService);
            } else {
                return;
            }
        }

        channelService = channelFactory.createNewChannel(event.getTenantId(), channelId, config);
        try {
            channelService.init();
            log.info("Started channel service");
            channels.put(channelId, channelService);
        } catch (Exception e) {
            log.error("Failed to init {} channel", channelService.getType(), e);
            // todo: write events log to channel entity
        }
    }

    private void stop(ChannelService<?> channelService) {
        try {
            channelService.stop();
            log.info("Stopped channel service");
        } catch (Exception e) {
            log.error("Failed to stop {} channel service", channelService.getType(), e);
            // todo: write events log to channel entity
        }
    }

    // TODO: THINK ABOUT: maybe will be nice to write proxy for every data storage which will automatically protect it from problems of concurrency

    // TODO: think about it. Telegram bot library on bot creating starts
    // new thread. It can cause thread-starvation, because it is not controlled by executor service. Maybe
    // it will be nice to proxy thread creating mechanism, or at least
    // using DiscoveryClient track heap state to start new instance of
    // telegram channel service


    private ChannelType getChannelType() {
        return channelFactory.getType();
    }

}
