package io.omnika.common.security.core.service;

import io.omnika.common.security.model.UserPrincipal;
import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    String USER_ID_CLAIM = "user_id";
    String USER_EMAIL_CLAIM = "email";
    String AUTHORITY_CLAIM = "authority";
    String TENANT_ID_CLAIM = "tenant_id";

    String extractToken(HttpServletRequest servletRequest);

    UserPrincipal parseToken(String token);

}
