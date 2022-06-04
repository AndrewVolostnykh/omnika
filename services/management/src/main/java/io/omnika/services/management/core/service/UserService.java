package io.omnika.services.management.core.service;

import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.common.rest.services.management.dto.auth.SetPasswordDto;
import io.omnika.common.rest.services.management.dto.auth.SigningDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import java.util.UUID;

public interface UserService {

    TokenDto signUp(SigningDto signingDto);

    TokenDto login(SigningDto signingDto);

    UserDto get(Long id);

    UserDto createNeedActivation(String email);

    TokenDto activate(UUID activationToken, SetPasswordDto setPasswordDto);

    UserDto getCurrent();
}
