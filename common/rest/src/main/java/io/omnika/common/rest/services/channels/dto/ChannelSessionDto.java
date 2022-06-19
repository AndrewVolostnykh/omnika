package io.omnika.common.rest.services.channels.dto;

import io.omnika.common.rest.services.management.model.ChannelType;
import java.util.UUID;
import lombok.Data;

@Data
public class ChannelSessionDto {

    private UUID id;
    private Long sessionId;
    private UUID channelId;
    private UUID tenantId;
    private ChannelType channelType;

}
