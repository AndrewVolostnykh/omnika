package io.omnika.services.management.web.controller;

import io.omnika.common.rest.controller.BaseController;
import io.omnika.common.rest.services.management.TenantController;
import io.omnika.common.rest.services.management.dto.Tenant;
import io.omnika.services.management.core.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TenantControllerImpl extends BaseController implements TenantController {

    private final TenantService tenantService;

    @Override
    public Tenant createTenant(Tenant tenant) {
        return tenantService.createTenant(tenant);
    }

    @Override
    public Tenant updateTenant(Tenant tenant) {
        return tenantService.updateTenant(tenant);
    }

    @Override
    public Tenant getTenant(UUID tenantId) {
        return tenantService.getTenantById(tenantId);
    }

    @Override
    public Tenant getCurrentTenant() {
        return tenantService.getTenantById(getTenantId());
    }

    @Override
    public List<Tenant> listTenants() {
        return tenantService.getTenants();
    }

}
