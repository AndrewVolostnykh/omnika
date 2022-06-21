package io.omnika.services.telegram.channel.service;

import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.common.rest.services.channels.dto.ChannelSessionDto;
import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelConfig;
import io.omnika.services.telegram.channel.service.saga.SendMessageToGatewaySaga;
import lombok.extern.slf4j.Slf4j;
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
    private final SendMessageToGatewaySaga sendMessageToGatewaySaga;

    public BotChannelService(ChannelDto metadata, SendMessageToGatewaySaga sendMessageToGatewaySaga) {
        super();
        this.metadata = metadata;
        this.botName = ((TelegramBotChannelConfig) metadata.getConfig()).getBotName();
        this.apiKey = ((TelegramBotChannelConfig) metadata.getConfig()).getApiKey();
        this.sendMessageToGatewaySaga = sendMessageToGatewaySaga;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        ChannelSessionDto channelSessionDto = new ChannelSessionDto();
        channelSessionDto.setTenantId(getMetadata().getTenantId());
        channelSessionDto.setChannelId(getMetadata().getId());
        channelSessionDto.setSessionId(message.getChatId());
        channelSessionDto.setChannelType(getMetadata().getChannelType());

        ChannelMessageDto channelMessageDto = new ChannelMessageDto();
        channelMessageDto.setText(message.getText());
        channelMessageDto.setChannelSessionDto(channelSessionDto);

        ChannelMessageDto sentMessage = sendMessageToGatewaySaga.send(channelMessageDto);

        // TODO: change to TRACE
        log.warn(
                "Sent message: text [{}], tenant id [{}], channel id [{}], session id [{}]",
                sentMessage.getText(),
                sentMessage.getChannelSessionDto().getTenantId(),
                sentMessage.getChannelSessionDto().getChannelId(),
                sentMessage.getChannelSessionDto().getSessionId()
        );
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
