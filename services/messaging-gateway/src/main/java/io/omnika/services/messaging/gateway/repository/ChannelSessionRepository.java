package io.omnika.services.messaging.gateway.repository;

import io.omnika.services.messaging.gateway.model.ChannelSessionEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelSessionRepository extends JpaRepository<ChannelSessionEntity, UUID> {

    boolean existsBySessionIdAndChannelId(String sessionId, UUID channelId);

    Optional<ChannelSessionEntity> findBySessionIdAndChannelId(String sessionId, UUID channelId);

}
