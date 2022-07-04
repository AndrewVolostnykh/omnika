package io.omnika.services.messaging.gateway.converter;

import io.omnika.common.core.converters.AbstractBasicEntityConverter;
import io.omnika.common.rest.services.channels.dto.SenderDto;
import io.omnika.services.messaging.gateway.converter.mappers.SenderMapper;
import io.omnika.services.messaging.gateway.model.Sender;

public class SenderConverter extends AbstractBasicEntityConverter<Sender, SenderDto> {

    protected SenderConverter() {
        super(SenderMapper.INSTANCE);
    }
}
