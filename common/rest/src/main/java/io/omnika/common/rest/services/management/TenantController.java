package io.omnika.common.rest.services.management;

import io.omnika.common.rest.services.management.dto.Tenant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Tag(name = "Tenant controller", description = "CRUDL for tenant entity")
@RequestMapping("/tenant")
public interface TenantController {

    @PostMapping
    @PreAuthorize("hasAuthority('SYSADMIN')")
    Tenant createTenant(@RequestBody Tenant tenant);

    @PutMapping
    @PreAuthorize("hasAuthority('TENANT_ADMIN')") // todo: think about sign up - fix api
    Tenant updateTenant(@RequestBody Tenant tenant);

    @GetMapping("/{tenantId}")
    @PreAuthorize("hasAuthority('SYSADMIN')")
    Tenant getTenant(@PathVariable UUID tenantId);

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    Tenant getCurrentTenant();

    @PreAuthorize("hasAuthority('SYSADMIN')")
    List<Tenant> listTenants();

}
