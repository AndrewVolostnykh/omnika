package io.omnika.common.exception.controller;

import io.omnika.common.exception.Error;
import io.omnika.common.exception.ExceptionCodes.Auth;
import io.omnika.common.exception.FieldError;
import io.omnika.common.exception.exceptions.ValidationException;
import io.omnika.common.exception.exceptions.auth.IncorrectPasswordException;
import io.omnika.common.exception.exceptions.auth.UserNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldError>> handleValidationException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors().stream()
                .map(objectError -> FieldError.builder().field(objectError.getField()).code(objectError.getDefaultMessage()).build())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(fieldErrors);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<List<FieldError>> handleCustomValidationException(ValidationException e) {
        return ResponseEntity.badRequest().body(e.getFieldErrors());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder().code(Auth.LOGIN_OR_PASSWORD_INCORRECT).build());
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<Error> handleIncorrectPasswordException(IncorrectPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder().code(Auth.LOGIN_OR_PASSWORD_INCORRECT).build());
    }
}
