package io.omnika.common.rest.services.management;

import io.omnika.common.rest.services.management.dto.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User controller", description = "Endpoints to operate with user")
@RequestMapping("/user")
public interface UserController {

    @Operation(description = "Get current user by token")
    @GetMapping("/current")
    User getCurrent();

    @Operation(description = "Query all users of current tenant")
    @GetMapping("/tenant/users")
    List<UserDto> getTenantsUsers();

    @GetMapping("/tenant/managers")
    List<UserDto> getTenantManagers();

}
