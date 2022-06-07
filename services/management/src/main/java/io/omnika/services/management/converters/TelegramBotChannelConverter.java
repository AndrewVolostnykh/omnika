package io.omnika.services.management.converters;

import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelDto;
import io.omnika.services.management.converters.mappers.TelegramChannelBotMapper;
import io.omnika.services.management.model.channel.TelegramBotChannel;
import org.springframework.stereotype.Component;

@Component
public class TelegramBotChannelConverter extends AbstractBasicConverter<TelegramBotChannel, TelegramBotChannelDto> {

    public TelegramBotChannelConverter() {
        super(TelegramChannelBotMapper.INSTANCE);
    }
}
