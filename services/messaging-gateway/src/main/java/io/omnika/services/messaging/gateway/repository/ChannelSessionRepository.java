package io.omnika.services.messaging.gateway.repository;

import io.omnika.services.messaging.gateway.model.ChannelSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChannelSessionRepository extends JpaRepository<ChannelSessionEntity, UUID> {

    Optional<ChannelSessionEntity> findByExternalIdAndChannelId(String sessionId, UUID channelId);

    Optional<ChannelSessionEntity> findByTenantIdAndId(UUID tenantId, UUID id);

    List<ChannelSessionEntity> findByTenantId(UUID tenantId);

}
