package io.omnika.common.rest.services.channels.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelMessageDto {

    private UUID id;
    private String text;
    private String internalId; // for now needed only for instagram. but take aware that will be nice to write it also for telegram
    private ChannelSessionDto channelSessionDto;
}
