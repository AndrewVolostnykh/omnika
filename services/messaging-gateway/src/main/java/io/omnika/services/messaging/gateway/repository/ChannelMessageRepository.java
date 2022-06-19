package io.omnika.services.messaging.gateway.repository;

import io.omnika.services.messaging.gateway.model.ChannelMessage;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelMessageRepository extends JpaRepository<ChannelMessage, UUID> {

}
