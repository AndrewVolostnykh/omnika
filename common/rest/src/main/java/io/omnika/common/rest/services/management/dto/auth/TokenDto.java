package io.omnika.common.rest.services.management.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {

    private String authToken;
}
