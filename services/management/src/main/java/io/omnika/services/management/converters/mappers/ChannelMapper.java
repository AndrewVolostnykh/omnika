package io.omnika.services.management.converters.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.services.management.model.channel.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChannelMapper extends BasicEntityMapper<Channel, ChannelDto> {

    ChannelMapper INSTANCE = Mappers.getMapper(ChannelMapper.class);

    @Override
    @Mapping(target = "tenant", expression = "java(new io.omnika.services.management.model.Tenant(channelDto.getTenantId()))")
    @Mapping(target = "config", expression = "java(io.omnika.common.utils.json.JacksonUtils.valueToTree(channelDto.getConfig()))")
    Channel toDomain(ChannelDto channelDto);

    @Override
    @Mapping(target = "tenantId", expression = "java(domain.getTenant().getId())")
    @Mapping(target = "config", expression = "java(io.omnika.common.utils.json.JacksonUtils.convert(domain.getConfig(), io.omnika.common.rest.services.management.dto.channel.ChannelConfig.class))")
    ChannelDto toDto(Channel domain);

}
