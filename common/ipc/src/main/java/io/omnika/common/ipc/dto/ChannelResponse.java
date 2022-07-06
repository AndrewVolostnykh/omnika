package io.omnika.common.ipc.dto;

import io.omnika.common.model.channel.ChannelConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelResponse {
    private UUID tenantId;
    private UUID channelId;
    private ChannelConfig channelConfig;
}
