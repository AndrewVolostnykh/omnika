package io.omnika.common.rest.services.management.dto.auth;

import io.omnika.common.exceptions.ExceptionCodes.Validation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SetPasswordDto {

    @NotNull(message = Validation.PASSWORD_REQUIRED)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = Validation.INVALID_PASSWORD_PATTERN)
    private String password;
}
