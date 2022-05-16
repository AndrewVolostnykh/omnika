package io.omnika.services.management.web.controller;

import io.omnika.common.exception.controller.BaseController;
import io.omnika.common.rest.services.management.AuthController;
import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.common.rest.services.management.dto.auth.SigningDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.omnika.services.management.core.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllerImpl extends BaseController implements AuthController {

    private final UserService userService;

    public UserDto signUp(@Valid SigningDto signingDto) {
        return userService.create(signingDto);
    }

    public TokenDto login(@Valid SigningDto signingDto) {
        return userService.login(signingDto);
    }
}
