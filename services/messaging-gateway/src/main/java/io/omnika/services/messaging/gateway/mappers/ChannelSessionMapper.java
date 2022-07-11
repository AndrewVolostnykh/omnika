package io.omnika.services.messaging.gateway.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.model.channel.ChannelSession;
import io.omnika.services.messaging.gateway.model.ChannelSessionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelSessionMapper extends BasicEntityMapper<ChannelSessionEntity, ChannelSession> {

    @Override
    ChannelSessionEntity toDomain(ChannelSession dto);

    @Override
    ChannelSession toDto(ChannelSessionEntity domain);

}
