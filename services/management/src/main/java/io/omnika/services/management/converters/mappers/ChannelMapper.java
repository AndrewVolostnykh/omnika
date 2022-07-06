package io.omnika.services.management.converters.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.model.channel.Channel;
import io.omnika.services.management.model.ChannelEntity;
import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.services.management.model.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChannelMapper extends BasicEntityMapper<ChannelEntity, Channel> {

    ChannelMapper INSTANCE = Mappers.getMapper(ChannelMapper.class);

    @Override
    @Mapping(target = "tenant", expression = "java(new io.omnika.services.management.model.Tenant(channelDto.getTenantId()))")
    @Mapping(target = "config", expression = "java(io.omnika.common.utils.json.JacksonUtils.valueToTree(channelDto.getConfig()))")
    @Mapping(target = "assignedUsers", expression = "java(channelDto.getAssignedUsers().stream().map(io.omnika.services.management.model.User::new).collect(java.util.stream.Collectors.toSet()))")
    Channel toDomain(ChannelDto channelDto);

    @Override
    @Mapping(target = "tenantId", expression = "java(domain.getTenant().getId())")
    @Mapping(target = "config", expression = "java(io.omnika.common.utils.json.JacksonUtils.convert(domain.getConfig(), io.omnika.common.rest.services.management.dto.channel.ChannelConfig.class))")
    @Mapping(target = "assignedUsers", expression = "java(domain.getAssignedUsers().stream().map(user -> user.getId()).collect(java.util.stream.Collectors.toSet()))")
    ChannelDto toDto(Channel domain);

}
