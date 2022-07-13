package io.omnika.services.messaging.gateway.service;

import io.omnika.common.model.channel.ChannelSession;
import io.omnika.services.messaging.gateway.core.service.ChannelSessionService;
import io.omnika.services.messaging.gateway.mappers.ChannelSessionMapper;
import io.omnika.services.messaging.gateway.model.ChannelSessionEntity;
import io.omnika.services.messaging.gateway.repository.ChannelSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ChannelSessionServiceImpl implements ChannelSessionService {

    private final ChannelSessionRepository channelSessionRepository;
    private final ChannelSessionMapper channelSessionMapper;

    @Override
    public ChannelSession createChannelSession(UUID tenantId, ChannelSession channelSession) {
        channelSession.setId(null);
        channelSession.setTenantId(tenantId);
        ChannelSessionEntity channelSessionEntity = channelSessionMapper.toDomain(channelSession);
        channelSessionEntity = channelSessionRepository.save(channelSessionEntity);
        return channelSessionMapper.toDto(channelSessionEntity);
    }

    @Override
    public ChannelSession findChannelSessionByExternalIdAndChannelId(String externalId, UUID channelId) {
        return channelSessionRepository.findByExternalIdAndChannelId(externalId, channelId)
                .map(channelSessionMapper::toDto)
                .orElse(null);
    }

    @Override
    public ChannelSession findChannelSessionByTenantIdAndId(UUID tenantId, UUID id) {
        return channelSessionRepository.findByTenantIdAndId(tenantId, id)
                .map(channelSessionMapper::toDto)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ChannelSession> findChannelSessionsByTenantId(UUID tenantId) {
        return channelSessionRepository.findByTenantId(tenantId).stream()
                .map(channelSessionMapper::toDto)
                .collect(Collectors.toList());
    }

}
