package io.omnika.common.rest.services.management;

import io.omnika.common.rest.services.management.dto.auth.SetPasswordDto;
import io.omnika.common.rest.services.management.dto.auth.SignUpDto;
import io.omnika.common.rest.services.management.dto.auth.SigningDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.omnika.common.rest.services.management.dto.manager.CreateManagerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth controller", description = "Endpoints to sign up or login")
@RequestMapping("/auth")
public interface AuthController {

    @Operation(description = "Create new user with email and password")
    @PostMapping("/sign-up")
    void signUp(@RequestBody SignUpDto signingDto);

    @PostMapping("/sign-up-manager")
    void signUpManager(CreateManagerDto createManagerDto); // TODO [viacheslav]: remove

    @PostMapping("/set-password/{activationToken}")
    TokenDto setPassword(@PathVariable UUID activationToken, @RequestBody SetPasswordDto setPasswordDto);

    @GetMapping("/activate/{activationToken}")
    TokenDto activateUser(@PathVariable UUID activationToken);

    @PostMapping("/login")
    TokenDto login(@RequestBody SigningDto signingDto);

    @GetMapping("/refresh")
    TokenDto refresh(HttpServletRequest request);
}
