package io.omnika.services.management.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.model.channel.Channel;
import io.omnika.services.management.model.ChannelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ChannelMapper extends BasicEntityMapper<ChannelEntity, Channel> {

    @Override
    @Mapping(target = "tenant", expression = "java(new io.omnika.services.management.model.TenantEntity(channel.getTenantId()))")
    @Mapping(target = "config", expression = "java(io.omnika.common.utils.json.JacksonUtils.valueToTree(channel.getConfig()))")
    @Mapping(target = "assignedUsers", expression = "java(channel.getAssignedUsers().stream().map(io.omnika.services.management.model.UserEntity::new).collect(java.util.stream.Collectors.toSet()))")
    ChannelEntity toDomain(Channel channel);

    @Override
    @Mapping(target = "tenantId", expression = "java(domain.getTenant().getId())")
    @Mapping(target = "config", expression = "java(io.omnika.common.utils.json.JacksonUtils.convert(domain.getConfig(), io.omnika.common.model.channel.ChannelConfig.class))")
    @Mapping(target = "assignedUsers", expression = "java(domain.getAssignedUsers().stream().map(user -> user.getId()).collect(java.util.stream.Collectors.toSet()))")
    Channel toDto(ChannelEntity domain);

}
