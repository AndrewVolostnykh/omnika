package io.omnika.services.management.core.service;

import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.common.rest.services.management.dto.auth.SigningDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;

public interface UserService {

    UserDto create(SigningDto signingDto);

    TokenDto login(SigningDto signingDto);
}
