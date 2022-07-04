package io.omnika.services.messaging.gateway.converter.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.rest.services.channels.dto.SenderDto;
import io.omnika.services.messaging.gateway.model.Sender;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SenderMapper extends BasicEntityMapper<Sender, SenderDto> {

    SenderMapper INSTANCE = Mappers.getMapper(SenderMapper.class);

}
