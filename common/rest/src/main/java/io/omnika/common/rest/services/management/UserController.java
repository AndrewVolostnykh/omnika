package io.omnika.common.rest.services.management;

import io.omnika.common.rest.services.management.dto.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User controller", description = "Endpoints to operate with user")
@RequestMapping("/user")
public interface UserController {

    @Operation(description = "Get current user by token")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    User getCurrentUser();

    @Operation(description = "Query all users of current tenant")
    @GetMapping("/tenant/users")
    @PreAuthorize("hasAnyAuthority('SYSADMIN', 'TENANT_ADMIN')")
    List<User> listTenantAdmins();

    @GetMapping("/tenant/managers")
    @PreAuthorize("hasAnyAuthority('SYSADMIN', 'TENANT_ADMIN', 'MANAGER')")
    List<User> listManagers();

}
