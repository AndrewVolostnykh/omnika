package io.omnika.common.ipc.events;

import io.omnika.common.model.channel.ServiceType;
import lombok.Data;

@Data
public class InstanceCountChangedEvent {
    private final ServiceType serviceType;
    private final int instanceCount;
}
