package io.omnika.services.messaging.gateway.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

// NOTE: very important to specify dates when
// message was created in messenger and other metadata
@Getter
@Setter
@Entity
@Table(name = "channel_messages")
@EqualsAndHashCode(callSuper = true)
public class ChannelMessageEntity extends BaseEntity {

    private String internalId;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private ChannelSessionEntity channelSession;

}
