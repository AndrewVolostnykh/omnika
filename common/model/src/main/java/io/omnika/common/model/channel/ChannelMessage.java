package io.omnika.common.model.channel;

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
    private String text;
    private String internalId; // for now needed only for instagram. but take aware that will be nice to write it also for telegram
    private String sessionId;

}
