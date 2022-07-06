package io.omnika.services.messaging.gateway.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "senders")
public class Sender extends BaseEntity {

    private String name;

    private String nickName;

    private String internalId;

    private String avatarUrl;

    @OneToOne(mappedBy = "sender")
    private ChannelSession channelSession;
}
