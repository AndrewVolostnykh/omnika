package io.omnika.services.messaging.gateway.converter;

import io.omnika.common.core.converters.AbstractBasicEntityConverter;
import io.omnika.common.model.channel.ChannelSession;
import io.omnika.services.messaging.gateway.converter.mappers.ChannelSessionMapper;
import io.omnika.services.messaging.gateway.model.ChannelSessionEntity;
import org.springframework.stereotype.Component;

@Component
public class ChannelSessionConverter extends AbstractBasicEntityConverter<ChannelSessionEntity, ChannelSession> {

    public ChannelSessionConverter() {
        super(ChannelSessionMapper.INSTANCE);
    }
}
