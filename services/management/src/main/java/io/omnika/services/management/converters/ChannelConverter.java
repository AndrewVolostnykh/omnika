package io.omnika.services.management.converters;

import io.omnika.common.core.converters.AbstractBasicEntityConverter;
import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.services.management.converters.mappers.ChannelMapper;
import io.omnika.services.management.model.channel.Channel;
import org.springframework.stereotype.Component;

@Component
public class ChannelConverter extends AbstractBasicEntityConverter<Channel, ChannelDto> {

    public ChannelConverter() {
        super(ChannelMapper.INSTANCE);
    }

}
