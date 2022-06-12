package io.omnika.services.management.converters;

import io.omnika.common.core.converters.AbstractBasicEntityConverter;
import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelDto;
import io.omnika.services.management.converters.mappers.TelegramChannelBotMapper;
import io.omnika.services.management.model.channel.TelegramBotChannel;
import org.springframework.stereotype.Component;

@Component
public class TelegramBotChannelConverter extends AbstractBasicEntityConverter<TelegramBotChannel, TelegramBotChannelDto> {

    public TelegramBotChannelConverter() {
        super(TelegramChannelBotMapper.INSTANCE);
    }
}
