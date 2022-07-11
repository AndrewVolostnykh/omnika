package io.omnika.services.messaging.gateway.model;

import io.omnika.common.model.channel.ChannelMessageType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

// NOTE: very important to specify dates when
// message was created in messenger and other metadata
@Getter
@Setter
@Entity
@Table(name = "channel_messages")
@EqualsAndHashCode(callSuper = true)
public class ChannelMessageEntity extends BaseEntity {

    private String externalId;

    private LocalDateTime receivedAt;

    private String text;

    @Enumerated(EnumType.STRING)
    private ChannelMessageType messageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private ChannelSessionEntity channelSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private SenderEntity sender;

}
