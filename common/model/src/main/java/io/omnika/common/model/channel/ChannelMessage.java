package io.omnika.common.model.channel;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelMessage {

    private UUID id;
    private ChannelMessageType messageType;
    private String text;
    private LocalDateTime receivedAt;
    private UUID channelSessionId;
    private UUID senderId;
    private String externalId;

}
