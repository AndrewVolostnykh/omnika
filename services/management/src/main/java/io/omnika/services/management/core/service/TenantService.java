package io.omnika.services.management.core.service;

import io.omnika.common.rest.services.management.dto.Tenant;

import java.util.UUID;

public interface TenantService {

    Tenant create(Tenant tenant);

    Tenant get(UUID tenantId);

}
