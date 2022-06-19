package io.omnika.services.management.core.service;

import io.omnika.common.rest.services.management.dto.TenantDto;
import java.util.UUID;

public interface TenantService {

    TenantDto create(TenantDto tenantDto);

    TenantDto get(UUID tenantId);

}
