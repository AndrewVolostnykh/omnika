package io.omnika.common.rest.controller;

import io.omnika.common.exceptions.Error;
import io.omnika.common.exceptions.ExceptionCodes.Auth;
import io.omnika.common.exceptions.FieldError;
import io.omnika.common.exceptions.FieldValueError;
import io.omnika.common.exceptions.GenericException;
import io.omnika.common.exceptions.ObjectNotFoundException;
import io.omnika.common.exceptions.ValidationException;
import io.omnika.common.exceptions.WithDescriptionError;
import io.omnika.common.exceptions.auth.IncorrectPasswordException;
import io.omnika.common.exceptions.auth.UserNotFoundException;
import io.omnika.common.security.model.UserPrincipal;
import io.omnika.common.security.utils.AuthenticationHelper;
import java.util.List;
import java.util.UUID;
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
                .map(objectError -> FieldError.builder()
                        .field(objectError.getField())
                        .code(objectError.getDefaultMessage())
                        .build())
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

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<WithDescriptionError> handleGenericException(GenericException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getError());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<List<FieldValueError>> handleObjectNotFoundException(ObjectNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getRequestParams());
    }

    /** Query user principal from token. Recommended to use
     * with {@link org.springframework.security.access.prepost.PreAuthorize} annotation */
    public UserPrincipal getPrincipal() {
        return AuthenticationHelper.getAuthenticationDetails();
    }

    public UUID getTenantId() {
        return getPrincipal().getTenantId();
    }

}
