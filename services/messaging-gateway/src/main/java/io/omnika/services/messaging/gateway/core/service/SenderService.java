package io.omnika.services.messaging.gateway.core.service;

import io.omnika.common.model.channel.ChannelType;
import io.omnika.common.model.channel.Sender;

public interface SenderService {

    Sender createSender(Sender sender);

    Sender findSenderByExternalIdAndChannelType(String externalId, ChannelType channelType);

    Sender findSenderByExternalId(String externalId);

}
