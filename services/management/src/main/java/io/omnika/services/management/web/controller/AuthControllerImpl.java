package io.omnika.services.management.web.controller;

import io.omnika.common.rest.controller.BaseController;
import io.omnika.common.rest.services.management.AuthController;
import io.omnika.common.rest.services.management.dto.auth.SetPasswordDto;
import io.omnika.common.rest.services.management.dto.auth.SigningDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.omnika.services.management.core.service.UserService;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthControllerImpl extends BaseController implements AuthController {

    private final UserService userService;

    @Override
    public TokenDto signUp(@Valid SigningDto signingDto) {
        return userService.signUp(signingDto);
    }

    @Override
    public TokenDto login(@Valid SigningDto signingDto) {
        return userService.login(signingDto);
    }

    @Override
    public TokenDto signUp(UUID activationToken, SetPasswordDto setPasswordDto) {
        return userService.activate(activationToken, setPasswordDto);
    }
}
