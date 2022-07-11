package io.omnika.common.ipc.events;

import lombok.Data;

@Data
public abstract class EntityEvent {
    protected EntityEventType eventType;
}
