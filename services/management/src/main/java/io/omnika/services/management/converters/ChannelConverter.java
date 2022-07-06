package io.omnika.services.management.converters;

import io.omnika.common.core.converters.AbstractBasicEntityConverter;
import io.omnika.common.model.channel.Channel;
import io.omnika.services.management.converters.mappers.ChannelMapper;
import io.omnika.services.management.model.Channel;
import io.omnika.services.management.model.ChannelEntity;
import org.springframework.stereotype.Component;

@Component
public class ChannelConverter extends AbstractBasicEntityConverter<ChannelEntity, Channel> {

    public ChannelConverter() {
        super(ChannelMapper.INSTANCE);
    }

}
