package io.omnika.common.channel.api.service;

import io.omnika.common.ipc.config.Topics;
import io.omnika.common.ipc.dto.ChannelResponse;
import io.omnika.common.ipc.dto.ChannelsRequest;
import io.omnika.common.ipc.service.PartitionService;
import io.omnika.common.ipc.service.QueueService;
import io.omnika.common.ipc.service.ServiceType;
import io.omnika.common.model.channel.ChannelConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelManager {

    private final ChannelFactory channelFactory;
    private final QueueService queueService;
    private final PartitionService partitionService;

    private final Map<UUID, ChannelService<?>> channels = new ConcurrentHashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void initChannels() {
        queueService.subscribe(Topics.newChannels(channelFactory.getType()), this::handleNewChannel);
        requestChannels();
    }

    private void requestChannels() {
        ChannelsRequest channelsRequest = ChannelsRequest.builder()
                .channelType(channelFactory.getType())
                .build();
        queueService.sendMessage(ServiceType.MANAGEMENT_SERVICE, Topics.getChannels(), channelsRequest,
                Topics.newChannels(channelFactory.getType()));
    }

    private void handleNewChannel(ChannelResponse channelResponse) {
        log.info("Handling new channel: {}", channelResponse);
        UUID tenantId = channelResponse.getTenantId();
        UUID channelId = channelResponse.getChannelId();
        ChannelConfig config = channelResponse.getChannelConfig();

        if (!partitionService.isMyPartition(channelId)) {
            return;
            // todo: handle discovery service events and update channels list
        }

        ChannelService<?> channelService = channels.get(channelId);
        if (channelService != null) {
            if (!channelService.getConfig().equals(config)) {
                try {
                    channelService.stop();
                } catch (Exception e) {
                    log.error("Failed to stop {} channel service", channelService.getType(), e);
                    // todo: write events log to channel entity
                }
            } else {
                return;
            }
        }

        channelService = channelFactory.createNewChannel(tenantId, channelId, config);
        try {
            channelService.init();
            channels.put(channelId, channelService);
        } catch (Exception e) {
            log.error("Failed to init {} channel", channelService.getType(), e);
            // todo: write events log to channel entity
        }
    }


    // TODO: THINK ABOUT: maybe will be nice to write proxy for every data storage which will automatically protect it from problems of concurrency

    // TODO: think about it. Telegram bot library on bot creating starts
    // new thread. It can cause thread-starvation, because it is not controlled by executor service. Maybe
    // it will be nice to proxy thread creating mechanism, or at least
    // using DiscoveryClient track heap state to start new instance of
    // telegram channel service


}
