package io.omnika.common.ipc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutboundChannelMessage {
    private String externalSessionId;
    private String text;
    private UUID channelId;
}
