package io.omnika.services.messaging.gateway.repository;

import io.omnika.services.messaging.gateway.model.ChannelSession;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<ChannelSession, Long> {

    boolean existsBySessionIdAndChannelId(Long sessionId, Long channelId);

    Optional<ChannelSession> findBySessionIdAndChannelId(Long sessionId, Long channelId);

}
