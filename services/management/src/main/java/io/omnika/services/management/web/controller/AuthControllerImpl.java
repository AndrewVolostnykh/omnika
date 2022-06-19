package io.omnika.services.management.web.controller;

import io.omnika.common.rest.controller.BaseController;
import io.omnika.common.rest.services.management.AuthController;
import io.omnika.common.rest.services.management.dto.auth.SetPasswordDto;
import io.omnika.common.rest.services.management.dto.auth.SignUpDto;
import io.omnika.common.rest.services.management.dto.auth.SigningDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.omnika.common.rest.services.management.dto.manager.CreateManagerDto;
import io.omnika.services.management.core.service.UserService;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl extends BaseController implements AuthController {

    private final UserService userService;

    @Override
    public void signUp(@Valid SignUpDto signingDto) {
        userService.signUp(signingDto);
    }

    @Override
    @PreAuthorize("hasRole('TENANT_ADMIN')")
    public void signUpManger(@Valid CreateManagerDto createManagerDto) {
        userService.signUpManager(getPrincipal().getTenantId(), createManagerDto);
    }

    @Override
    public TokenDto setPassword(UUID activationToken, SetPasswordDto setPasswordDto) {
        return userService.setPassword(activationToken, setPasswordDto);
    }

    @Override
    public TokenDto activateUser(UUID activationToken) {
        return userService.activate(activationToken);
    }

//    @Override
//    public TokenDto signUp(@Valid SigningDto signingDto) {
//        return userService.signUp(signingDto);
//    }

    @Override
    public TokenDto login(SigningDto signingDto) {
        return userService.login(signingDto);
    }
//
//    @Override
//    public TokenDto signUp(UUID activationToken, SetPasswordDto setPasswordDto) {
//        return userService.activate(activationToken, setPasswordDto);
//    }

    // creating user with organization (tenant)
    // ?creating manager for organization (tenant)
    // activating user
    // login
}
