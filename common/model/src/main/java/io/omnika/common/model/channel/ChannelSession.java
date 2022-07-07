package io.omnika.common.model.channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelSession {

    private UUID id;
    private LocalDateTime receivedAt;
    private String sessionId;
    private UUID channelId;
    private UUID tenantId;
    private ChannelType channelType;
    private SenderDto sender;
}
