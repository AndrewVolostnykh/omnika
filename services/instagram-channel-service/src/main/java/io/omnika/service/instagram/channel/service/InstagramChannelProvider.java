package io.omnika.service.instagram.channel.service;

import io.omnika.common.ipc.streams.SendToStream;
import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.common.rest.services.management.model.ChannelType;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstagramChannelProvider {
    private static final String GET_ALL_INSTAGRAM_CHANNELS = "getAllChannels";

    private final StreamBridge streamBridge;
    private final ExecutorService workStealingPool;

    private final Map<UUID, InstagramChannel> instagramChannels = new ConcurrentHashMap<>();

    @SendToStream(exchange = GET_ALL_INSTAGRAM_CHANNELS)
    @EventListener(ApplicationReadyEvent.class)
    public ChannelType onStartupRequestAllChannels() {
        log.warn("Sending trigger to get active instagram channels");
        return ChannelType.INSTAGRAM;
    }

    // Currently we receiving all channels needs to be listened
    // but in future when a size of active channels will be
    // close to thousands or more, it will be a problem
    // TODO: develop queued-data-pagination abstractions and processors
    @Bean
    public Consumer<List<ChannelDto>> allInstagramChannels() {
        return channels -> channels.forEach(channel -> {
            // FIXME: remove id
            // TODO: cover it with async processing, but not just for each
            log.warn("[ALL CHANNELS EVENT] Channel received. Name [{}], channel id [{}], tenant name [{}]", channel.getName(), channel.getId(), channel.getTenantId());
            createChannel(channel);
        });
    }

    @Bean
    public Consumer<ChannelDto> newInstagramChannel() {
        return channel -> {
            log.warn("[NEW CHANNEL EVENT] New channel received. Bot [{}], channel id [{}], tenant name [{}]", channel.getName(), channel.getId(), channel.getTenantId());
            createChannel(channel);
        };
    }

    @Bean
    public Consumer<ChannelMessageDto> toInstagramChannelMessage() {
        return message -> instagramChannels.get(message.getChannelSessionDto().getChannelId()).sendMessage(message);
    }

    // TODO: think about it. Telegram bot library on bot creating starts
    // new thread. It can cause thread-starvation, because it is not controlled by executor service. Maybe
    // it will be nice to proxy thread creating mechanism, or at least
    // using DiscoveryClient track heap state to start new instance of
    // telegram channel service
    @SneakyThrows
    public void createChannel(ChannelDto channelDto) {
        log.warn(
                "Registering bot channel [{}], id [{}], tenant id [{}]",
                channelDto.getName(),
                channelDto.getId(),
                channelDto.getTenantId()
        );

        InstagramChannel instagramChannel = new InstagramChannel(channelDto, streamBridge, true, workStealingPool);
        instagramChannels.put(channelDto.getId(), instagramChannel);
        instagramChannel.start();
    }
}
