package io.omnika.services.management.converters.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.rest.services.management.dto.TenantDto;
import io.omnika.services.management.model.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TenantMapper extends BasicEntityMapper<Tenant, TenantDto> {

    TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);
}
