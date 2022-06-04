package io.omnika.services.management.mappers;

import io.omnika.common.rest.services.management.dto.TenantDto;
import io.omnika.services.management.model.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TenantMapper {

    TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);

    TenantDto tenantToDto(Tenant tenant);

    Tenant tenantDtoToDomain(TenantDto tenantDto);

}
