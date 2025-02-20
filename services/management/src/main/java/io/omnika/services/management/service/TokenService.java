package io.omnika.services.management.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.omnika.common.rest.services.management.dto.User;
import io.omnika.common.security.service.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
class TokenService extends TokenServiceImpl {
    @Value("${security.token.lifetime}")
    private Integer tokenLifetime;

    @Value("${security.token.refresh_lifetime}")
    private Integer refreshTokenLifetime;

    public String createToken(User user) {
        return Jwts.builder()
                .claim(USER_ID_CLAIM, user.getId())
                .claim(USER_EMAIL_CLAIM, user.getEmail())
                .claim(AUTHORITY_CLAIM, user.getAuthority())
                .claim(TENANT_ID_CLAIM, user.getTenantId())
                .claim(ISSUED_AT, Instant.now().toEpochMilli())
                // TODO: investigate should it be system default zone id or not
                .claim(EXPIRATION_DATE, Instant.now().plusSeconds(tokenLifetime).toEpochMilli())
                .signWith(SignatureAlgorithm.HS512, tokenSigningKey)
                .compact();
    }

    public String createRefreshToken(User user) {
        return Jwts.builder()
                .claim(USER_ID_CLAIM, user.getId())
                .claim(USER_EMAIL_CLAIM, user.getEmail())
                .claim(ISSUED_AT, Instant.now().toEpochMilli())
                // TODO: investigate should it be system default zone id or not
                .claim(EXPIRATION_DATE, Instant.now().plusSeconds(refreshTokenLifetime).toEpochMilli())
                .signWith(SignatureAlgorithm.HS512, tokenSigningKey)
                .compact();
    }
}
