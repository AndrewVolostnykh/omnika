package io.omnika.services.management.web.controller;

import io.omnika.common.rest.controller.BaseController;
import io.omnika.common.rest.services.management.UserController;
import io.omnika.common.rest.services.management.dto.User;
import io.omnika.common.security.model.Authority;
import io.omnika.services.management.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl extends BaseController implements UserController {

    private final UserService userService;

    @Override
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @Override
    public List<User> listTenantAdmins() {
        return userService.getUsersByTenantIdAndAuthority(getTenantId(), Authority.TENANT_ADMIN);
    }

    @Override
    public List<User> listManagers() {
        return userService.getUsersByTenantIdAndAuthority(getTenantId(), Authority.MANAGER);
    }

}
