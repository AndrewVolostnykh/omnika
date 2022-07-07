package io.omnika.common.ipc.dto;

import io.omnika.common.model.channel.ChannelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InboundChannelMessage {
    private ChannelType channelType;
    private String chatId;
    private String text;
}
