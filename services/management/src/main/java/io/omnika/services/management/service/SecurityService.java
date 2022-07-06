package io.omnika.services.management.service;

import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.omnika.services.management.converters.UserConverter;
import io.omnika.services.management.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final TokenService tokenService;
    private final UserConverter userConverter;

    public TokenDto refresh(UserDto user) {

        User domain = userConverter.toDomain(user);

        String accessToken = tokenService.createToken(domain);
        String newRefreshToken = tokenService.createRefreshToken(domain);

        return new TokenDto(accessToken, newRefreshToken);
    }
}
