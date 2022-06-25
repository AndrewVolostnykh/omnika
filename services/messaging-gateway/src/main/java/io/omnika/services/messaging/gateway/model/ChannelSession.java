package io.omnika.services.messaging.gateway.model;

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
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "channel_sessions")
public class ChannelSession extends BaseEntity {

    private String sessionId;

    private LocalDateTime receivedAt;

    private UUID channelId;

    private UUID tenantId;

    @Enumerated(EnumType.STRING)
    private ChannelType channelType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private Sender sender;

}
