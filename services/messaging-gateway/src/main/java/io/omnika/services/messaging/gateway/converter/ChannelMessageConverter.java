package io.omnika.services.messaging.gateway.converter;

import io.omnika.common.core.converters.AbstractBasicEntityConverter;
import io.omnika.common.model.channel.ChannelMessage;
import io.omnika.services.messaging.gateway.converter.mappers.ChannelMessageMapper;
import io.omnika.services.messaging.gateway.model.ChannelMessageEntity;
import org.springframework.stereotype.Component;

@Component
public class ChannelMessageConverter extends AbstractBasicEntityConverter<ChannelMessageEntity, ChannelMessage> {

    public ChannelMessageConverter() {
        super(ChannelMessageMapper.INSTANCE);
    }
}
