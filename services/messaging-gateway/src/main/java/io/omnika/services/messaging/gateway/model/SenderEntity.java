package io.omnika.services.messaging.gateway.model;

import io.omnika.common.model.channel.ChannelType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "senders")
public class SenderEntity extends BaseEntity {

    private String name;
    private String nickName;
    private String avatarUrl;

    private String externalId;
    private ChannelType channelType;

    public SenderEntity(UUID id) {
        this.setId(id);
    }

}
