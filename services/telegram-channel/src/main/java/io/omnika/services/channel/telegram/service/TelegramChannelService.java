package io.omnika.services.channel.telegram.service;

import io.omnika.common.channel.api.service.ChannelService;
import io.omnika.common.ipc.dto.InboundChannelMessage;
import io.omnika.common.ipc.dto.OutboundChannelMessage;
import io.omnika.common.model.channel.ChannelType;
import io.omnika.common.model.channel.TelegramBotChannelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.UUID;

@Service
@Scope("prototype")
public class TelegramChannelService extends ChannelService<TelegramBotChannelConfig> {

    private TelegramBotService botService;
    @Autowired
    private TelegramBotsApi telegramBotsApi;

    public TelegramChannelService(UUID tenantId, UUID channelId, TelegramBotChannelConfig config) throws Exception {
        super(tenantId, channelId, config);
    }

    @Override
    public void init() throws Exception {
        botService = new TelegramBotService(config, this);
        telegramBotsApi.registerBot(botService);
    }

    @Override
    protected void sendMessage(OutboundChannelMessage message) throws Exception {
        botService.sendMessage(message.getExternalSessionId(), message.getText());
    }

    protected void handleNewMessage(Message telegramMessage) {
        InboundChannelMessage message = InboundChannelMessage.builder()
                .id(telegramMessage.getMessageId().toString())
                .sessionId(telegramMessage.getChatId().toString())
                .userId(telegramMessage.getFrom().getId().toString())
                .text(telegramMessage.getText())
                .build();
        onNewMessage(message);
    }

    @Override
    public void stop() throws Exception {
        botService.clearWebhook();
    }

    @Override
    public ChannelType getType() {
        return ChannelType.TELEGRAM_BOT;
    }

}
