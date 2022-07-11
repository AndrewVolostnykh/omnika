package io.omnika.services.messaging.gateway.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.model.channel.ChannelMessage;
import io.omnika.services.messaging.gateway.model.ChannelMessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChannelMessageMapper extends BasicEntityMapper<ChannelMessageEntity, ChannelMessage> {

    @Override
    @Mapping(target = "channelSession", expression = "java(new io.omnika.services.messaging.gateway.model.ChannelSessionEntity(dto.getChannelSessionId()))")
    @Mapping(target = "sender", expression = "java(new io.omnika.services.messaging.gateway.model.SenderEntity(dto.getSenderId()))")
    ChannelMessageEntity toDomain(ChannelMessage dto);

    @Override
    @Mapping(target = "channelSessionId", expression = "java(domain.getChannelSession().getId())")
    @Mapping(target = "senderId", expression = "java(domain.getSender().getId())")
    ChannelMessage toDto(ChannelMessageEntity domain);
}
