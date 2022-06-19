package io.omnika.services.messaging.gateway.converter;

import io.omnika.common.core.converters.AbstractBasicEntityConverter;
import io.omnika.common.rest.services.channels.dto.ChannelSessionDto;
import io.omnika.services.messaging.gateway.converter.mappers.ChannelSessionMapper;
import io.omnika.services.messaging.gateway.model.ChannelSession;
import org.springframework.stereotype.Component;

@Component
public class ChannelSessionConverter extends AbstractBasicEntityConverter<ChannelSession, ChannelSessionDto> {

    public ChannelSessionConverter() {
        super(ChannelSessionMapper.INSTANCE);
    }
}
