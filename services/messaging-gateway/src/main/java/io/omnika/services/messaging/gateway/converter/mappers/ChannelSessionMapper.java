package io.omnika.services.messaging.gateway.converter.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.rest.services.channels.dto.ChannelSessionDto;
import io.omnika.services.messaging.gateway.model.ChannelSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChannelSessionMapper extends BasicEntityMapper<ChannelSession, ChannelSessionDto> {

    ChannelSessionMapper INSTANCE = Mappers.getMapper(ChannelSessionMapper.class);

    @Override
    @Mapping(target = "sender", expression = "java(SenderMapper.INSTANCE.toDomain(dto.getSender()))")
    ChannelSession toDomain(ChannelSessionDto dto);

    @Override
    @Mapping(target = "sender", expression = "java(SenderMapper.INSTANCE.toDto(domain.getSender()))")
    ChannelSessionDto toDto(ChannelSession domain);
}
