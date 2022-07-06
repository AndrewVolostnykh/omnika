package io.omnika.services.channel.telegram.service;

import io.omnika.common.channel.api.service.ChannelService;
import io.omnika.common.model.channel.ChannelType;
import io.omnika.common.model.channel.TelegramBotChannelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;

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
    protected void sendMessage(String sessionId, String text) throws Exception {
        botService.sendMessage(sessionId, text);
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
