package io.omnika.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.omnika.common.security.core.service.TokenService;
import io.omnika.common.security.exception.ProcessingTokenException;
import io.omnika.common.security.model.AccountPrincipal;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
@RequiredArgsConstructor
public class RequestFilter extends GenericFilterBean {

    private final TokenService tokenService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = tokenService.extractToken((HttpServletRequest) servletRequest);
        Authentication authentication = null;

        if (StringUtils.isNotEmpty(token)) {
            AccountPrincipal principal;
            try {
                principal = tokenService.parseToken(token);
            } catch (Exception e) {
                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                try (OutputStream outputStream = httpServletResponse.getOutputStream()) {
                    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(outputStream, new ProcessingTokenException(ExceptionUtils.getMessage(e)));
                    httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                    return;
                }
            }
            authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
