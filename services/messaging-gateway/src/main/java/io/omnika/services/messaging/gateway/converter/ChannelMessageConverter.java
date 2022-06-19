package io.omnika.services.messaging.gateway.converter;

import io.omnika.common.core.converters.AbstractBasicEntityConverter;
import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.services.messaging.gateway.converter.mappers.ChannelMessageMapper;
import io.omnika.services.messaging.gateway.model.ChannelMessage;
import org.springframework.stereotype.Component;

@Component
public class ChannelMessageConverter extends AbstractBasicEntityConverter<ChannelMessage, ChannelMessageDto> {

    public ChannelMessageConverter() {
        super(ChannelMessageMapper.INSTANCE);
    }
}
