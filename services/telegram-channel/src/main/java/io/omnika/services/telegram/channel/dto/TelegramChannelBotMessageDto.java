package io.omnika.services.telegram.channel.dto;

import io.omnika.common.rest.services.channels.dto.ChannelMessage;
import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelDto;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TelegramChannelBotMessageDto extends ChannelMessage<TelegramBotChannelDto> implements Serializable {

    public TelegramChannelBotMessageDto(String text, TelegramBotChannelDto telegramBotChannelDto) {
        super(text, telegramBotChannelDto);
    }
}

