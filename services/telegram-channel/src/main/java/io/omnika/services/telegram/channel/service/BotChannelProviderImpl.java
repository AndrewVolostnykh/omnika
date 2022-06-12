package io.omnika.services.telegram.channel.service;

import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelDto;
import io.omnika.common.rest.services.management.model.ChannelType;
import io.omnika.services.telegram.channel.core.service.BotChannelProvider;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotChannelProviderImpl implements BotChannelProvider {

    private final TelegramBotsApi telegramBotsApi;
    private final StreamBridge streamBridge;

    /* key is a channel id, value is a bot interface */
    // pay attention: concurrent hash map without additional lockers can produce concurrency-problems
    // TODO: THINK ABOUT: maybe will be nice to write proxy for every data storage which will automatically protect it from problems of concurrency
    private final Map<Long, BotChannelService> bots = new ConcurrentHashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void onStartupRequestAllChannels() {
        log.info("Sending trigger to get active telegram channels");
        streamBridge.send("getAllChannels-in-0", ChannelType.TELEGRAM_BOT);
    }

    // Currently we receiving all channels needs to be listened
    // but in future when a size of active channels will be
    // close to thousands or more, it will be a problem
    // TODO: develop queued-data-pagination abstractions and processors
    @Bean
    public Consumer<List<TelegramBotChannelDto>> allChannels() {
        return channels -> channels.forEach(channel -> {
            // FIXME: remove id
            // TODO: cover it with async processing, but not just for each
            log.warn("[ALL CHANNELS EVENT] Channel received. Name [{}], channel id [{}], tenant name [{}]", channel.getName(), channel.getId(), channel.getTenantDto().getName());
            createBot(channel);
        });
    }

    @Bean
    public Consumer<TelegramBotChannelDto> newTelegramChannel() {
        return channel -> {
            log.warn("[NEW CHANNEL EVENT] New channel received. Bot [{}], channel id [{}], tenant name [{}]", channel.getName(), channel.getId(), channel.getTenantDto().getName());
            createBot(channel);
        };
    }

    @Bean
    public Consumer<ChannelMessageDto> toTelegramChannelMessage() {
        return message -> bots.get(message.getChannelSessionDto().getChannelId()).sendMessage(message);
    }

    @SneakyThrows
    public void createBot(TelegramBotChannelDto telegramBotChannelDto) {
        log.warn(
                "Registering bot channel [{}], id [{}], tenant name [{}]",
                telegramBotChannelDto.getName(),
                telegramBotChannelDto.getId(),
                telegramBotChannelDto.getTenantDto().getName()
        );

        BotChannelService botChannelService = new BotChannelService(telegramBotChannelDto, streamBridge);
        telegramBotsApi.registerBot(botChannelService);
        bots.put(telegramBotChannelDto.getId(), botChannelService);
    }

//    @Bean
//    @SneakyThrows
    public void stopChannel(Long channelId) {
        Optional.ofNullable(bots.remove(channelId)).ifPresent(toStopChannel -> {
            log.info("Bot [{}], channel id [{}] stopped", toStopChannel.getBotUsername(), toStopChannel.getMetadata().getId());
            try {
                toStopChannel.clearWebhook();
            } catch (TelegramApiRequestException e) {
                log.error("Exception on stopping channel", e);
            }
        });
    }

    public List<TelegramBotChannelDto> list() {
        return bots.values().stream().map(BotChannelService::getMetadata).collect(Collectors.toList());
    }

}
