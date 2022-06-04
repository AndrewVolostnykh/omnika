package io.omnika.common.rest.services.management;

import io.omnika.common.rest.services.management.dto.auth.SetPasswordDto;
import io.omnika.common.rest.services.management.dto.auth.SigningDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth controller", description = "Endpoints to sign up or login")
@RestController
@RequestMapping("/auth")
public interface AuthController {

    @Operation(description = "Create new user with email and password")
    @PostMapping("/sign-up")
    TokenDto signUp(@RequestBody SigningDto signingDto);

    @PostMapping("/{activationToken}")
    TokenDto signUp(@PathVariable UUID activationToken, @RequestBody SetPasswordDto setPasswordDto);

    @PostMapping("/login")
    TokenDto login(@RequestBody SigningDto signingDto);
}
