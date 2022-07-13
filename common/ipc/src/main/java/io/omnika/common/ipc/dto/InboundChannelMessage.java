package io.omnika.common.ipc.dto;

import io.omnika.common.model.channel.ChannelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InboundChannelMessage {
    private String id;
    private String sessionId;
    private String userId;
    private String userName;
    private String text;
    private ChannelType channelType;
    private UUID channelId;
    private UUID tenantId;
}
