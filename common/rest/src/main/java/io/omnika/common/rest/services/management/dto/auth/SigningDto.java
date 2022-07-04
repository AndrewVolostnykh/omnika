package io.omnika.common.rest.services.management.dto.auth;

import io.omnika.common.exceptions.ExceptionCodes.Validation;
import io.omnika.common.rest.services.management.constraints.ValidPassword;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SigningDto {

    @NotNull(message = Validation.EMAIL_REQUIRED)
    @Email(message = Validation.INVALID_EMAIL_PATTERN)
    private String email;

    @NotNull(message = Validation.PASSWORD_REQUIRED)
    @ValidPassword
    private String password;

}
