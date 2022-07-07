package io.omnika.services.messaging.gateway.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "senders")
public class SenderEntity extends BaseEntity {

    private String name;

    private String nickName;

    private String internalId;

    private String avatarUrl;

    @OneToOne(mappedBy = "sender")
    private ChannelSessionEntity channelSession;

}
