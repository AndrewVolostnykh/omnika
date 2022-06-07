package io.omnika.services.management.converters.mappers;

import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.services.management.core.converter.mapper.BasicMapper;
import io.omnika.services.management.model.channel.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChannelMapper extends BasicMapper<Channel, ChannelDto> {

    ChannelMapper INSTANCE = Mappers.getMapper(ChannelMapper.class);

    @Override
    @Mapping(target = "tenant", expression = "java(TenantMapper.INSTANCE.toDomain(channelDto.getTenantDto()))")
    Channel toDomain(ChannelDto channelDto);

}
