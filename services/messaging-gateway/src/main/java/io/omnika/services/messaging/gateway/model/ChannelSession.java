package io.omnika.services.messaging.gateway.model;

import io.omnika.common.rest.services.management.model.ChannelType;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "channel_sessions")
public class ChannelSession extends BaseEntity {

    private Long sessionId; // should it have name like entity + id? Also interesting how it will work with viber/instagram

    private UUID channelId;

    private UUID tenantId;

    @Enumerated(EnumType.STRING)
    private ChannelType channelType;

}
