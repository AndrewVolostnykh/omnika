package io.omnika.common.exceptions;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    private final List<FieldError> fieldErrors;

    public ValidationException(List<FieldError> fieldErrors) {
        this.fieldErrors = List.copyOf(fieldErrors);
    }

    public ValidationException(FieldError fieldError) {
        this(List.of(fieldError));
    }

    public ValidationException(String errorCode) {
        FieldError fieldError = FieldError.builder()
                .code(errorCode)
                .build();
        this.fieldErrors = List.of(fieldError);
    }

    @Override
    public String getMessage() {
        return fieldErrors.stream()
                .map(FieldError::getCode)
                .collect(Collectors.joining(", "));
    }
}
