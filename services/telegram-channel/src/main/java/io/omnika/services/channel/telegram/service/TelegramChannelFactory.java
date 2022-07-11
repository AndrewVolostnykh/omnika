package io.omnika.services.channel.telegram.service;

import io.omnika.common.channel.api.service.ChannelFactory;
import io.omnika.common.model.channel.ChannelType;
import io.omnika.common.model.channel.TelegramBotChannelConfig;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Primary
public class TelegramChannelFactory implements ChannelFactory<TelegramBotChannelConfig> {

    @Override
    @Lookup
    public TelegramChannelService createNewChannel(UUID tenantId, UUID channelId, TelegramBotChannelConfig config) {
        return null;
    }

    @Override
    public ChannelType getType() {
        return ChannelType.TELEGRAM_BOT;
    }

}
