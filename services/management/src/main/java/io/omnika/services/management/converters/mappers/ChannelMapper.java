package io.omnika.services.management.converters.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.model.channel.Channel;
import io.omnika.services.management.model.ChannelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChannelMapper extends BasicEntityMapper<ChannelEntity, Channel> {

    ChannelMapper INSTANCE = Mappers.getMapper(ChannelMapper.class);

    @Override
    @Mapping(target = "tenant", expression = "java(new io.omnika.services.management.model.TenantEntity(channel.getTenantId()))")
    @Mapping(target = "config", expression = "java(io.omnika.common.utils.json.JacksonUtils.valueToTree(channel.getConfig()))")
    ChannelEntity toDomain(Channel channel);

    @Override
    @Mapping(target = "tenantId", expression = "java(domain.getTenant().getId())")
    @Mapping(target = "config", expression = "java(io.omnika.common.utils.json.JacksonUtils.convert(domain.getConfig(), io.omnika.common.model.channel.ChannelConfig.class))")
    Channel toDto(ChannelEntity domain);

}
