package io.omnika.services.management.web.controller;

import io.omnika.common.rest.controller.BaseController;
import io.omnika.common.rest.services.management.UserController;
import io.omnika.common.rest.services.management.dto.User;
import io.omnika.services.management.core.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl extends BaseController implements UserController {

    private final UserService userService;

    @Override
    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'MANAGER')")
    public User getCurrent() {
        return userService.getCurrent();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN')")
    public List<UserDto> getTenantsUsers() {
        return userService.list(getPrincipal().getTenantId());
    }

    @Override
    public List<UserDto> getTenantManagers() {
        return userService.listManagers(getPrincipal().getTenantId());
    }
}
