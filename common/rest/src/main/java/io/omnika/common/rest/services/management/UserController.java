package io.omnika.common.rest.services.management;

import io.omnika.common.rest.services.management.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User controller", description = "Endpoints to operate with user")
@PreAuthorize("isAuthenticated()")
@RequestMapping("/user")
public interface UserController {

    @Operation(description = "Get current user by token")
    @GetMapping("/current")
    UserDto getCurrent();

}
