package io.omnika.services.messaging.gateway.core;

import io.omnika.common.model.channel.ChannelMessage;
import io.omnika.common.model.channel.ChannelType;

public interface MessageProducer {

    ChannelMessage send(ChannelMessage channelMessage);

    boolean isApplicable(ChannelType channelType);
}
