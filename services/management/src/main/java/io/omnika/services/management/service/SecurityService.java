package io.omnika.services.management.service;

import io.omnika.common.rest.services.management.dto.User;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.omnika.services.management.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final TokenService tokenService;

    public TokenDto refresh(User user) {
        String accessToken = tokenService.createToken(user);
        String newRefreshToken = tokenService.createRefreshToken(user);

        return new TokenDto(accessToken, newRefreshToken);
    }
}
