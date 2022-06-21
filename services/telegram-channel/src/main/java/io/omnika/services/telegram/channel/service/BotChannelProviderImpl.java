package io.omnika.services.telegram.channel.service;

import io.omnika.common.ipc.streams.SendToStream;
import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.common.rest.services.management.model.ChannelType;
import io.omnika.services.telegram.channel.core.service.BotChannelProvider;
import io.omnika.services.telegram.channel.service.saga.SendMessageToGatewaySaga;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotChannelProviderImpl implements BotChannelProvider {

    private static final String GET_ALL_CHANNELS_EXCHANGE = "getAllChannels";

    private final TelegramBotsApi telegramBotsApi;
    private final SendMessageToGatewaySaga sendMessageToGatewaySaga;

    /* key is a channel id, value is a bot interface */
    // pay attention: concurrent hash map without additional lockers can produce concurrency-problems
    // TODO: THINK ABOUT: maybe will be nice to write proxy for every data storage which will automatically protect it from problems of concurrency
    // TODO: also maybe it will be nice to add all bots to context, make it spring beans
    private final Map<UUID, BotChannelService> bots = new ConcurrentHashMap<>();

    @SendToStream(exchange = GET_ALL_CHANNELS_EXCHANGE)
    @EventListener(ApplicationReadyEvent.class)
    public ChannelType onStartupRequestAllChannels() {
        log.info("Sending trigger to get active telegram channels");
//        streamBridge.send("getAllChannels-in-0", ChannelType.TELEGRAM_BOT);
        return ChannelType.TELEGRAM_BOT;
    }

    // Currently we receiving all channels needs to be listened
    // but in future when a size of active channels will be
    // close to thousands or more, it will be a problem
    // TODO: develop queued-data-pagination abstractions and processors
    @Bean
    public Consumer<List<ChannelDto>> allChannels() {
        return channels -> channels.forEach(channel -> {
            // FIXME: remove id
            // TODO: cover it with async processing, but not just for each
            log.warn("[ALL CHANNELS EVENT] Channel received. Name [{}], channel id [{}], tenant name [{}]", channel.getName(), channel.getId(), channel.getTenantId());
            createBot(channel);
        });
    }

    @Bean
    public Consumer<ChannelDto> newTelegramChannel() {
        return channel -> {
            log.warn("[NEW CHANNEL EVENT] New channel received. Bot [{}], channel id [{}], tenant name [{}]", channel.getName(), channel.getId(), channel.getTenantId());
            createBot(channel);
        };
    }

    @Bean
    public Consumer<ChannelMessageDto> toTelegramChannelMessage() {
        return message -> bots.get(message.getChannelSessionDto().getChannelId()).sendMessage(message);
    }

    @SneakyThrows
    public void createBot(ChannelDto channelDto) {
        log.warn(
                "Registering bot channel [{}], id [{}], tenant id [{}]",
                channelDto.getName(),
                channelDto.getId(),
                channelDto.getTenantId()
        );

        BotChannelService botChannelService = new BotChannelService(channelDto, sendMessageToGatewaySaga);
        telegramBotsApi.registerBot(botChannelService);
        bots.put(channelDto.getId(), botChannelService);
    }

//    @Bean
//    @SneakyThrows
    public void stopChannel(UUID channelId) {
        Optional.ofNullable(bots.remove(channelId)).ifPresent(toStopChannel -> {
//            log.info("Bot [{}], channel id [{}] stopped", toStopChannel.getBotUsername(), toStopChannel.getMetadata().getId());
            try {
                toStopChannel.clearWebhook();
            } catch (TelegramApiRequestException e) {
                log.error("Exception on stopping channel", e);
            }
        });
    }

    public List<ChannelDto> list() {
        return bots.values().stream().map(BotChannelService::getMetadata).collect(Collectors.toList());
    }

}
