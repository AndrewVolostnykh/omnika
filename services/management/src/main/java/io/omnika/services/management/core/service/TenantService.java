package io.omnika.services.management.core.service;

import io.omnika.common.rest.services.management.dto.Tenant;

import java.util.List;
import java.util.UUID;

public interface TenantService {

    Tenant createTenant(Tenant tenant);

    Tenant updateTenant(Tenant tenant);

    Tenant getTenantById(UUID id);

    List<Tenant> getTenants();

}
