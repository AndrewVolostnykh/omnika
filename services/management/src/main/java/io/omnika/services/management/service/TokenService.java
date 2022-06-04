package io.omnika.services.management.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.omnika.common.security.service.TokenServiceImpl;
import io.omnika.services.management.model.User;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
class TokenService extends TokenServiceImpl {
    @Value("${security.token.lifetime}")
    private Integer tokenLifetime;

    public String createToken(User user) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(tokenLifetime)))
                .claim(USER_ID_CLAIM, user.getId())
                .claim(USER_EMAIL_CLAIM, user.getEmail())
                .claim(AUTHORITY_CLAIM, user.getAuthority())
                .signWith(SignatureAlgorithm.HS512, tokenSigningKey)
                .compact();
    }
}
