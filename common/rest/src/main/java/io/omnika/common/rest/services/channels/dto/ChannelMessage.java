package io.omnika.common.rest.services.channels.dto;

import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public abstract class ChannelMessage<T extends ChannelDto> {

//  private Long chatId;
    private String text;
    private T metadata;
}
