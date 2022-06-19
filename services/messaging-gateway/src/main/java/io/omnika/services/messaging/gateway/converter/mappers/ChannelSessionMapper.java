package io.omnika.services.messaging.gateway.converter.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.rest.services.channels.dto.ChannelSessionDto;
import io.omnika.services.messaging.gateway.model.ChannelSession;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChannelSessionMapper extends BasicEntityMapper<ChannelSession, ChannelSessionDto> {

    ChannelSessionMapper INSTANCE = Mappers.getMapper(ChannelSessionMapper.class);

}
