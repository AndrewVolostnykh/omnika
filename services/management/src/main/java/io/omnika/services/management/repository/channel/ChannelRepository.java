package io.omnika.services.management.repository.channel;

import io.omnika.common.model.channel.ChannelType;
import io.omnika.services.management.model.ChannelEntity;
import io.omnika.services.management.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {

    List<ChannelEntity> findAllByTenantIdAndChannelType(UUID tenantId, ChannelType channelType);

    Page<ChannelEntity> findByChannelType(ChannelType channelType, Pageable pageable);

    List<ChannelEntity> findAllByTenantId(UUID tenantId);

    List<ChannelEntity> findAllByAssignedUsersContainsAndTenantId(UserEntity user, UUID tenantId);

}
