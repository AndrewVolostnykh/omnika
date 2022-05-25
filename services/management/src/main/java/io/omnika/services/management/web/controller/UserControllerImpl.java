package io.omnika.services.management.web.controller;

import io.omnika.common.rest.services.management.UserController;
import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.services.management.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public UserDto getCurrent() {
        return userService.getCurrent();
    }
}
