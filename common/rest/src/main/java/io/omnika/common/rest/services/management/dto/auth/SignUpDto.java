package io.omnika.common.rest.services.management.dto.auth;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpDto extends SigningDto {

    public SignUpDto(String email, String password, String tenantName) {
        super(email, password);
        this.tenantName = tenantName;
    }

    @NotNull
    @NotEmpty
    private String tenantName;
}
