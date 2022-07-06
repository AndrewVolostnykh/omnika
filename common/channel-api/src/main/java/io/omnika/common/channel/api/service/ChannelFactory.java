package io.omnika.common.channel.api.service;

import io.omnika.common.model.channel.ChannelConfig;
import io.omnika.common.model.channel.ChannelType;

import java.util.UUID;

public interface ChannelFactory<C extends ChannelConfig> {

    ChannelService<C> createNewChannel(UUID tenantId, UUID channelId, C config);

    ChannelType getType();

    static ChannelType aba() {
        return ChannelType.TELEGRAM_BOT;
    }

}
