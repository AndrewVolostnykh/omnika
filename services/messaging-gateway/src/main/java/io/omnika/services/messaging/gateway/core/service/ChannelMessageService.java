package io.omnika.services.messaging.gateway.core.service;

import io.omnika.common.model.channel.ChannelMessage;

import java.util.List;
import java.util.UUID;

public interface ChannelMessageService {

    ChannelMessage createChannelMessage(ChannelMessage channelMessage);

    List<ChannelMessage> findChannelMessagesByChannelSessionId(UUID channelSessionId);

}
