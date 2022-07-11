package io.omnika.services.messaging.gateway.model;

import io.omnika.common.model.channel.ChannelType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "channel_sessions")
public class ChannelSessionEntity extends BaseEntity {

    private String externalId;

    // id of user for assignment. Every of tenant can see every of sessions
    // if assigned id = tenant id - managers will not see this session
    private UUID assignedToUser;

    private UUID channelId;

    private UUID tenantId;

    @Enumerated(EnumType.STRING)
    private ChannelType channelType;

    public ChannelSessionEntity(UUID id) {
        this.setId(id);
    }

}
