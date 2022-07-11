package io.omnika.common.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.omnika.common.security.core.service.TokenService;
import io.omnika.common.security.model.Authority;
import io.omnika.common.security.model.UserPrincipal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TokenServiceImpl implements TokenService {

    @Value("${security.token.signing_key}")
    protected String tokenSigningKey;
    @Getter
    @Value("${security.token.request_header:Authorization}")
    private String tokenRequestHeader;

    @Override
    public String extractToken(HttpServletRequest servletRequest) {
        return Optional.ofNullable(servletRequest.getHeader(tokenRequestHeader))
                .map(authHeaderValue -> StringUtils.substringAfter(StringUtils.trimToEmpty(authHeaderValue), "Bearer "))
                .orElse(null);
    }

    @Override
    public UserPrincipal parseToken(String token) {
        Claims claims = getAllClaims(token);

        UUID userId = UUID.fromString(claims.get(USER_ID_CLAIM, String.class));
        String email = claims.get(USER_EMAIL_CLAIM, String.class);
        UUID tenantId = UUID.fromString(claims.get(TENANT_ID_CLAIM, String.class));
        Authority accountAuthority = Authority.valueOf(claims.get(AUTHORITY_CLAIM, String.class));

        GrantedAuthority authority = new SimpleGrantedAuthority(accountAuthority.name());

        UserPrincipal principal = new UserPrincipal();
        principal.setUserId(userId);
        principal.setEmail(email);
        principal.setTenantId(tenantId);
        principal.setAuthority(authority);
        principal.setToken(token);
        return principal;
    }

    protected Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(tokenSigningKey)
                .parseClaimsJws(token).getBody();
    }

    @Override
    public boolean isTokenExpired(String token) {
        return Instant.now().isAfter(
                Instant.ofEpochMilli(getClaimFromToken(token, claims -> (Long) claims.get(TokenService.EXPIRATION_DATE)))
        );
//        return getClaimFromToken(token, claims -> (Long) claims.get(TokenService.EXPIRATION_DATE)) > Instant.now().isAfter();
    }

    @Override
    public UUID getUserId(String token) {
        return getClaimFromToken(token, claims -> claims.get(USER_ID_CLAIM, UUID.class));
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getAllClaims(token));
    }

}
