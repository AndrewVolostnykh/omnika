package io.omnika.common.rest.services.management;

import io.omnika.common.rest.services.management.dto.TenantDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Tenant controller", description = "CRUDL for tenant entity")
@PreAuthorize("isAuthenticated()")
@RequestMapping("/tenant")
public interface TenantController {

    @GetMapping("/{tenantId}")
    TenantDto get(@PathVariable("tenantId") UUID tenantId);

    @GetMapping
    TenantDto get();

}
