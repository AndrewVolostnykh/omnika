package io.omnika.common.rest.services.management.dto;

import io.omnika.common.exceptions.ExceptionCodes.Validation;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateManagerDto {

    private String name;

    @NotNull(message = Validation.EMAIL_REQUIRED)
    @Email(message = Validation.INVALID_EMAIL_PATTERN)
    private String email;

    @NotNull
    private Long tenantId;
}
