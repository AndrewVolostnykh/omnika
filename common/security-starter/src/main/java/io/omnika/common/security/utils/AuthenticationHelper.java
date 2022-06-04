package io.omnika.common.security.utils;

import io.omnika.common.exceptions.auth.AuthenticationException;
import io.omnika.common.security.model.UserPrincipal;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationHelper {

    public static UserPrincipal getAuthenticationDetails() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(UserPrincipal.class::isInstance)
                .map(UserPrincipal.class::cast)
                .orElseThrow(AuthenticationException::new);
    }

}
