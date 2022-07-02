package io.omnika.services.management.core.service;

import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.common.rest.services.management.dto.auth.SetPasswordDto;
import io.omnika.common.rest.services.management.dto.auth.SignUpDto;
import io.omnika.common.rest.services.management.dto.auth.SigningDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.omnika.common.rest.services.management.dto.manager.CreateManagerDto;
import java.util.List;
import java.util.UUID;

public interface UserService {

    void signUp(SignUpDto signUpDto);

    void signUpManager(UUID tenantId, CreateManagerDto createManagerDto);

    TokenDto login(SigningDto signingDto);

    TokenDto activate(UUID activationToken);

    TokenDto setPassword(UUID activationToken, SetPasswordDto setPasswordDto);

    UserDto getCurrent();

    List<UserDto> list(UUID tenantId);

    UserDto get(UUID id);

    List<UserDto> listManagers(UUID tenantId);
}
