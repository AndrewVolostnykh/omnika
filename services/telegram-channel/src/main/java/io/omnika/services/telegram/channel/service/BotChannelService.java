package io.omnika.services.telegram.channel.service;

import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class BotChannelService extends TelegramLongPollingBot {

    private final TelegramBotChannelDto metadata;
    private final String botName;
    private final String apiKey;
    private final StreamBridge streamBridge;

    public BotChannelService(TelegramBotChannelDto metadata, StreamBridge streamBridge) {
        super();
        this.metadata = metadata;
        this.botName = metadata.getBotName();
        this.apiKey = metadata.getApiKey();
        this.streamBridge = streamBridge;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.warn("Bot {} with channel id {} and tenant name {} received message\n{}",
                getMetadata().getName(),
                getMetadata().getId(),
                getMetadata().getTenantDto().getName(),
                update.getMessage().getText()
        );

//        TelegramChannelBotMessageDto receivedMessage = new TelegramChannelBotMessageDto(update.getMessage().getText(), metadata);
//
//        Message<TelegramChannelBotMessageDto> message = MessageBuilder.withPayload(receivedMessage)
//                .setHeader("sessionId", update.getMessage().getChatId())
//                .build();
//
//        streamBridge.send("telegramBotMessage-in-0", message);
    }

    public TelegramBotChannelDto getMetadata() {
        return this.metadata;
    }

    @Override
    public String getBotToken() {
        return this.apiKey;
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }
}
