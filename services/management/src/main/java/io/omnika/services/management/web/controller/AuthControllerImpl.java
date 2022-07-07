package io.omnika.services.management.web.controller;

import io.omnika.common.rest.controller.BaseController;
import io.omnika.common.rest.services.management.AuthController;
import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.common.rest.services.management.dto.auth.SetPasswordDto;
import io.omnika.common.rest.services.management.dto.auth.SignUpDto;
import io.omnika.common.rest.services.management.dto.auth.SigningDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.omnika.common.rest.services.management.dto.manager.CreateManagerDto;
import io.omnika.common.security.core.service.TokenService;
import io.omnika.services.management.core.service.UserService;
import io.omnika.services.management.service.SecurityService;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl extends BaseController implements AuthController {

    private final UserService userService;
    private final SecurityService securityService;
    private final TokenService tokenService;

    @Override
    public void signUp(@Valid SignUpDto signingDto) {
        userService.signUp(signingDto);
    }

    @Override
    @PreAuthorize("hasAuthority('TENANT_ADMIN')")
    public UserDto signUpManger(@Valid CreateManagerDto createManagerDto) {
        return userService.signUpManager(getPrincipal().getTenantId(), createManagerDto);
    }

    @GetMapping("/test")
    public String testConnection() {
        return "connection succeed";
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

    @Override
    public TokenDto refresh(HttpServletRequest request) {
        UUID userId = tokenService.getUserId(tokenService.extractToken(request));
        return securityService.refresh(userService.get(userId));
    }
//
//    @Override
//    public TokenDto signUp(UUID activationToken, SetPasswordDto setPasswordDto) {
//        return userService.activate(activationToken, setPasswordDto);
//    }
}
