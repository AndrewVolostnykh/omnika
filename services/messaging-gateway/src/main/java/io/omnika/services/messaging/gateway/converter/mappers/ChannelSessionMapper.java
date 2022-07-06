package io.omnika.services.messaging.gateway.converter.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.model.channel.ChannelSession;
import io.omnika.services.messaging.gateway.model.ChannelSessionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChannelSessionMapper extends BasicEntityMapper<ChannelSessionEntity, ChannelSession> {

    ChannelSessionMapper INSTANCE = Mappers.getMapper(ChannelSessionMapper.class);

}
