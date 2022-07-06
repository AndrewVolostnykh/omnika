package io.omnika.services.channel.telegram.service;

import io.omnika.common.model.channel.TelegramBotChannelConfig;
import lombok.Getter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBotService extends TelegramLongPollingBot {

    @Getter
    private final String botUsername;
    @Getter
    private final String botToken;
    private final TelegramChannelService channelService;

    public TelegramBotService(TelegramBotChannelConfig config, TelegramChannelService channelService) {
        this.botUsername = config.getBotName();
        this.botToken = config.getApiKey();
        this.channelService = channelService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        channelService.onNewMessage(message.getChatId().toString(), message.getText());
    }

    public void sendMessage(String chatId, String text) throws TelegramApiException {
        execute(SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build());
    }

}
