package io.omnika.common.ipc.events;

import io.omnika.common.model.channel.ChannelConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChannelEntityEvent extends EntityEvent {
    private UUID tenantId;
    private UUID channelId;
    private ChannelConfig channelConfig;

    public ChannelEntityEvent(UUID tenantId, UUID channelId, ChannelConfig channelConfig, EntityEventType eventType) {
        this.tenantId = tenantId;
        this.channelId = channelId;
        this.channelConfig = channelConfig;
        this.eventType = eventType;
    }

    public ChannelEntityEvent(UUID tenantId, UUID channelId, ChannelConfig channelConfig) {
        this.tenantId = tenantId;
        this.channelId = channelId;
        this.channelConfig = channelConfig;
    }
}
