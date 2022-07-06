package io.omnika.services.messaging.gateway.model;

import io.omnika.common.model.channel.ChannelType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "channel_sessions")
public class ChannelSessionEntity extends BaseEntity {

    private String sessionId;

    private UUID channelId;

    private UUID tenantId;

    @Enumerated(EnumType.STRING)
    private ChannelType channelType;

}
