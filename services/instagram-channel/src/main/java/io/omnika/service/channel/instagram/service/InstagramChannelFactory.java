package io.omnika.service.channel.instagram.service;

import io.omnika.common.channel.api.service.ChannelFactory;
import io.omnika.common.model.channel.ChannelType;
import io.omnika.common.model.channel.InstagramChannelConfig;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InstagramChannelFactory extends ChannelFactory<InstagramChannelConfig> {

    @Lookup
    @Override
    protected InstagramChannelService createNewChannel(UUID tenantId, UUID channelId, InstagramChannelConfig config) {
        return null;
    }

    @Override
    protected ChannelType getType() {
        return ChannelType.INSTAGRAM;
    }

    // Currently we receiving all channels needs to be listened
    // but in future when a size of active channels will be
    // close to thousands or more, it will be a problem
    // TODO: develop queued-data-pagination abstractions and processors


    // TODO: think about it. Telegram bot library on bot creating starts
    // new thread. It can cause thread-starvation, because it is not controlled by executor service. Maybe
    // it will be nice to proxy thread creating mechanism, or at least
    // using DiscoveryClient track heap state to start new instance of
    // telegram channel service

}
