package io.omnika.services.messaging.gateway.repository;

import io.omnika.common.model.channel.ChannelType;
import io.omnika.services.messaging.gateway.model.SenderEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenderRepository extends JpaRepository<SenderEntity, UUID> {
    SenderEntity findByExternalIdAndChannelType(String externalId, ChannelType channelType);

    SenderEntity findByExternalId(String externalId);

}
