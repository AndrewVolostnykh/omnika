package io.omnika.services.messaging.gateway.converter.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.services.messaging.gateway.model.ChannelMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChannelMessageMapper extends BasicEntityMapper<ChannelMessage, ChannelMessageDto> {

    ChannelMessageMapper INSTANCE = Mappers.getMapper(ChannelMessageMapper.class);

    @Override
    @Mapping(target = "channelSession", expression = "java(ChannelSessionMapper.INSTANCE.toDomain(dto.getChannelSessionDto()))")
    ChannelMessage toDomain(ChannelMessageDto dto);

    @Override
    @Mapping(target = "channelSessionDto", expression = "java(ChannelSessionMapper.INSTANCE.toDto(domain.getChannelSession()))")
    ChannelMessageDto toDto(ChannelMessage domain);
}
