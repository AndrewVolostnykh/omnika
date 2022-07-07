package io.omnika.services.messaging.gateway.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.model.channel.ChannelSession;
import io.omnika.services.messaging.gateway.model.ChannelSessionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ChannelSessionMapper extends BasicEntityMapper<ChannelSessionEntity, ChannelSession> {

    @Override
    @Mapping(target = "sender", expression = "java(SenderMapper.INSTANCE.toDomain(dto.getSender()))")
    ChannelSessionEntity toDomain(ChannelSession dto);

    @Override
    @Mapping(target = "sender", expression = "java(SenderMapper.INSTANCE.toDto(domain.getSender()))")
    ChannelSession toDto(ChannelSessionEntity domain);

}
