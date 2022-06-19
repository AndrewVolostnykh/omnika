package io.omnika.services.messaging.gateway.core;

import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.common.rest.services.management.model.ChannelType;

public interface MessageProducer {

    ChannelMessageDto send(ChannelMessageDto channelMessageDto);

    boolean isApplicable(ChannelType channelType);
}
