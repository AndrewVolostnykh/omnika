package io.omnika.common.security.filter;

import io.omnika.common.security.core.service.TokenService;
import io.omnika.common.security.model.Authority;
import io.omnika.common.security.model.UserPrincipal;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestFilter extends OncePerRequestFilter {

    private final TokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = tokenService.extractToken(request);
        Authentication authentication = null;

        // TODO: add check for an expiration of token
        if (StringUtils.isNotEmpty(token) && !tokenService.isTokenExpired(token)) {
            UserPrincipal principal;

            try {
                principal = tokenService.parseToken(token);
                authentication = new UsernamePasswordAuthenticationToken(principal, null, List.of(principal.getAuthority()));
            } catch (Exception e) {
                log.error("Error on processing auth token occurred", e);
                return;
            }
        } else {
            authentication = new AnonymousAuthenticationToken("some key", new UserPrincipal(), List.of(new SimpleGrantedAuthority(Authority.ANONYMOUS.name())));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return "/actuator/health".equals(path);
    }

}
