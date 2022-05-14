package io.omnika.common.rest.services.management.dto.auth;

import io.omnika.common.exception.ExceptionCodes.Auth;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SigningDto {

    @NotNull(message = Auth.EMAIL_REQUIRED)
    @Email(message = Auth.INVALID_EMAIL_PATTERN)
    private String email;
    @NotNull(message = Auth.PASSWORD_REQUIRED)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = Auth.INVALID_PASSWORD_PATTERN)
    private String password;

}
