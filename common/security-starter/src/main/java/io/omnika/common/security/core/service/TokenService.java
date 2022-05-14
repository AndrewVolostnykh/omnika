package io.omnika.common.security.core.service;

import io.omnika.common.security.model.AccountPrincipal;
import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    String USER_ID_CLAIM = "user_id";
    String USER_EMAIL_CLAIM = "email";
    String AUTHORITY_CLAIM = "authority";

    String extractToken(HttpServletRequest servletRequest);

    AccountPrincipal parseToken(String token);

}
