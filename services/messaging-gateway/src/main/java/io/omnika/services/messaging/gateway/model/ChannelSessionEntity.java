package io.omnika.services.messaging.gateway.model;

import io.omnika.common.model.channel.ChannelType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import io.omnika.common.rest.services.management.model.ChannelType;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "channel_sessions")
public class ChannelSessionEntity extends BaseEntity {

    private String sessionId;

    private LocalDateTime receivedAt;

    // id of user for assignment. Every of tenant can see every of sessions
    // if assigned id = tenant id - managers will not see this session
    private UUID assignedToUser;

    private UUID channelId;

    private UUID tenantId;

    @Enumerated(EnumType.STRING)
    private ChannelType channelType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private Sender sender;

}
