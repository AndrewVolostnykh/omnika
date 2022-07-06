package io.omnika.services.management.core.service;

import io.omnika.common.rest.services.management.dto.User;
import io.omnika.common.rest.services.management.dto.auth.SetPasswordDto;
import io.omnika.common.rest.services.management.dto.auth.SignUpDto;
import io.omnika.common.rest.services.management.dto.auth.SigningDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.omnika.common.rest.services.management.dto.manager.CreateManagerDto;
import java.util.UUID;

public interface UserService {

    void signUp(SignUpDto signUpDto);

    void signUpManager(UUID tenantId, CreateManagerDto createManagerDto);

    TokenDto login(SigningDto signingDto);

//    UserDto get(Long id);
//
//    UserDto createNeedActivation(String email);

    TokenDto activate(UUID activationToken);

    TokenDto setPassword(UUID activationToken, SetPasswordDto setPasswordDto);

    User getCurrent();
}
