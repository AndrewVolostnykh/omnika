package io.omnika.common.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.omnika.common.security.core.service.TokenService;
import io.omnika.common.security.model.Authority;
import io.omnika.common.security.model.UserPrincipal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
    @Value("${security.token.request_header:X-Authorization}")
    private String tokenRequestHeader;

    @Override
    public String extractToken(HttpServletRequest servletRequest) {
        return StringUtils.trimToNull(servletRequest.getHeader(tokenRequestHeader));
    }

    @Override
    public UserPrincipal parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSigningKey)
                .parseClaimsJws(token).getBody();

        Long userId = claims.get(USER_ID_CLAIM, Long.class);
        String email = claims.get(USER_EMAIL_CLAIM, String.class);
        Authority accountAuthority = Authority.valueOf(claims.get(AUTHORITY_CLAIM, String.class));

        List<GrantedAuthority> authorities = Arrays.stream(Authority.values())
                .filter(authority -> authority.ordinal() <= accountAuthority.ordinal())
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toList());

        UserPrincipal principal = new UserPrincipal();
        principal.setUserId(userId);
        principal.setEmail(email);
        principal.setAuthorities(authorities);
        principal.setToken(token);
        return principal;
    }

}
