package io.omnika.services.messaging.gateway.repository;

import io.omnika.services.messaging.gateway.model.ChannelSession;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelSessionRepository extends JpaRepository<ChannelSession, UUID> {

    boolean existsBySessionIdAndChannelId(Long sessionId, UUID channelId);

    Optional<ChannelSession> findBySessionIdAndChannelId(Long sessionId, UUID channelId);

}
