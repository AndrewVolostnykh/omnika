package io.omnika.services.messaging.gateway.core.service;

import io.omnika.common.model.channel.ChannelSession;

import java.util.UUID;

public interface ChannelSessionService {

    ChannelSession createChannelSession(UUID tenantId, ChannelSession session);

    ChannelSession findChannelSessionByExternalIdAndChannelId(String sessionId, UUID channelId);

    ChannelSession findChannelSessionByTenantIdAndId(UUID tenantId, UUID id);

}
