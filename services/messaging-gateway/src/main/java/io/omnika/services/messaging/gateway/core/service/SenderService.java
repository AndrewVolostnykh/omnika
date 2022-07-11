package io.omnika.services.messaging.gateway.core.service;

import io.omnika.common.model.channel.ChannelType;
import io.omnika.common.model.channel.Sender;

import java.util.UUID;

public interface SenderService {

    Sender createSender(Sender sender);

    Sender findSenderByExternalIdAndChannelType(String externalId, ChannelType channelType);

    Sender findSenderById(UUID id);

}
