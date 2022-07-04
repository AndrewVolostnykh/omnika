package io.omnika.common.security.core.service;

import io.omnika.common.security.model.UserPrincipal;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    String USER_ID_CLAIM = "user_id";
    String USER_EMAIL_CLAIM = "email";
    String AUTHORITY_CLAIM = "authority";
    String TENANT_ID_CLAIM = "tenant_id";
    String ISSUED_AT = "issued_at";
    String EXPIRATION_DATE = "expiration_date";

    String extractToken(HttpServletRequest servletRequest);

    UserPrincipal parseToken(String token);

    boolean isTokenExpired(String token);

    UUID getUserId(String token);
}
