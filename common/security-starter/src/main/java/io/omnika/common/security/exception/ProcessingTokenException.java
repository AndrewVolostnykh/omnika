package io.omnika.common.security.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ProcessingTokenException extends RuntimeException {
    public ProcessingTokenException(String message) {
        super(message);
    }
}
