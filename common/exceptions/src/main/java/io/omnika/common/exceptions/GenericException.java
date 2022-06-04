package io.omnika.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericException extends RuntimeException {

    private final WithDescriptionError error;

    public GenericException(WithDescriptionError error) {
        this.error = error;
    }
}
