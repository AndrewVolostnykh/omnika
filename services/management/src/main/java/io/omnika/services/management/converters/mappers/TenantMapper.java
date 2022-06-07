package io.omnika.services.management.converters.mappers;

import io.omnika.common.rest.services.management.dto.TenantDto;
import io.omnika.services.management.core.converter.mapper.BasicMapper;
import io.omnika.services.management.model.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TenantMapper extends BasicMapper<Tenant, TenantDto> {

    TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);
}
