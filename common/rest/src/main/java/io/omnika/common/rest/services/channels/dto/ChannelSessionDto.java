package io.omnika.common.rest.services.channels.dto;

import io.omnika.common.rest.services.management.model.ChannelType;
import lombok.Data;

@Data
public class ChannelSessionDto {

    private Long id;
    private Long sessionId;
    private Long channelId;
    private Long tenantId;
    private ChannelType channelType;

}
