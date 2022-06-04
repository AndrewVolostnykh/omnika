package io.omnika.common.rest.services.management;

import io.omnika.common.rest.services.management.dto.TenantDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Tenant controller", description = "CRUDL for tenant entity")
@PreAuthorize("isAuthenticated()")
@RequestMapping("/tenant")
public interface TenantController {

    @PostMapping
    TenantDto create(@RequestBody TenantDto tenantDto);

    @PutMapping
    TenantDto update(@RequestBody TenantDto tenantDto);

    @GetMapping
    List<TenantDto> list();

    @GetMapping("/{tenantId}")
    TenantDto get(@PathVariable("tenantId") Long tenantId);

}
