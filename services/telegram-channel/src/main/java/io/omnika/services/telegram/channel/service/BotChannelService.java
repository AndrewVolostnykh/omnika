package io.omnika.services.telegram.channel.service;

import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.common.rest.services.channels.dto.ChannelSessionDto;
import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class BotChannelService extends TelegramLongPollingBot {

    private final ChannelDto metadata;
    private final String botName;
    private final String apiKey;
    private final StreamBridge streamBridge;

    public BotChannelService(ChannelDto metadata, StreamBridge streamBridge) {
        super();
        this.metadata = metadata;
        this.botName = ((TelegramBotChannelConfig) metadata.getConfig()).getBotName();
        this.apiKey = ((TelegramBotChannelConfig) metadata.getConfig()).getApiKey();
        this.streamBridge = streamBridge;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        log.warn("Bot {} with channel id {} and tenant name {} received message\n{}",
                getMetadata().getName(),
                getMetadata().getId(),
                getMetadata().getTenantId(),
                message.getText()
        );

        ChannelSessionDto channelSessionDto = new ChannelSessionDto();
        channelSessionDto.setTenantId(getMetadata().getTenantId());
        channelSessionDto.setChannelId(getMetadata().getId());
        channelSessionDto.setSessionId(message.getChatId());
        channelSessionDto.setChannelType(getMetadata().getChannelType());

        ChannelMessageDto channelMessageDto = new ChannelMessageDto();
        channelMessageDto.setText(message.getText());
        channelMessageDto.setChannelSessionDto(channelSessionDto);

        streamBridge.send("message-in-0", channelMessageDto);
    }

    public void sendMessage(ChannelMessageDto channelMessageDto) {
        try {

            log.warn("Message to send to channel [{}] with session [{}] with text [{}]",
                    channelMessageDto.getChannelSessionDto().getChannelId(),
                    channelMessageDto.getChannelSessionDto().getSessionId(),
                    channelMessageDto.getText());

            execute(new SendMessage(channelMessageDto.getChannelSessionDto().getSessionId().toString(), channelMessageDto.getText()));
        } catch (TelegramApiException e) {
            log.error("Cannot send message", e);
        }
    }

    public ChannelDto getMetadata() {
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
