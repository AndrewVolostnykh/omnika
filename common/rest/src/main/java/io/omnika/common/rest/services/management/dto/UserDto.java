package io.omnika.common.rest.services.management.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String email;
    private UUID activationToken;
}
