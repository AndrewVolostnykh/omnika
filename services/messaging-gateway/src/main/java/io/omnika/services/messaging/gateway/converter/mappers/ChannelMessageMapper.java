package io.omnika.services.messaging.gateway.converter.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.model.channel.ChannelMessage;
import io.omnika.services.messaging.gateway.model.ChannelMessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChannelMessageMapper extends BasicEntityMapper<ChannelMessageEntity, ChannelMessage> {

    ChannelMessageMapper INSTANCE = Mappers.getMapper(ChannelMessageMapper.class);

//    @Override
//    @Mapping(target = "channelSession", expression = "java(ChannelSessionMapper.INSTANCE.toDomain(dto.getChannelSessionDto()))")
//    ChannelMessageEntity toDomain(ChannelMessage dto);
//
//    @Override
//    @Mapping(target = "channelSessionDto", expression = "java(ChannelSessionMapper.INSTANCE.toDto(domain.getChannelSession()))")
//    ChannelMessage toDto(ChannelMessageEntity domain);
}
