package io.omnika.services.management.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.rest.services.management.dto.Tenant;
import io.omnika.services.management.model.TenantEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface TenantMapper extends BasicEntityMapper<TenantEntity, Tenant> {

}
