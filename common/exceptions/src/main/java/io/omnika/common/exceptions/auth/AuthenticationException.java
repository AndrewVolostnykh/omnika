package io.omnika.common.exceptions.auth;

import io.omnika.common.exceptions.BasicException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends BasicException {

    public AuthenticationException(String code) {
        super(code);
    }
}
