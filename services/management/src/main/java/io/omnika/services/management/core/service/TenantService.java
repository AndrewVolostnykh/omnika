package io.omnika.services.management.core.service;

import io.omnika.common.rest.services.management.dto.TenantDto;
import java.util.List;

public interface TenantService {

    TenantDto create(TenantDto tenantDto);

    TenantDto update(TenantDto tenantDto);

    List<TenantDto> list();

    TenantDto get(Long tenantId);

}
