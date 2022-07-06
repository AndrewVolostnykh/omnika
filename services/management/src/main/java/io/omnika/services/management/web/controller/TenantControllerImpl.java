package io.omnika.services.management.web.controller;

import io.omnika.common.rest.controller.BaseController;
import io.omnika.common.rest.services.management.TenantController;
import io.omnika.common.rest.services.management.dto.Tenant;
import io.omnika.services.management.core.service.TenantService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TenantControllerImpl extends BaseController implements TenantController {

    private final TenantService tenantService;

    public Tenant get(UUID tenantId) {
        return tenantService.get(tenantId);
    }

}
