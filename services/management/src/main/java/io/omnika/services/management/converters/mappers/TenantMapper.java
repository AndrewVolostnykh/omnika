package io.omnika.services.management.converters.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.rest.services.management.dto.Tenant;
import io.omnika.services.management.model.TenantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TenantMapper extends BasicEntityMapper<TenantEntity, Tenant> {

    TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);
}
