package io.omnika.common.rest.services.management.dto;

import com.fasterxml.jackson.databind.JsonNode;
import io.omnika.common.security.model.Authority;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class User {

    private UUID id;
    private String name;
    private String email;
    private boolean active;
    private UUID activationToken;
    private Authority authority;
    private UUID tenantId;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private JsonNode metadata;

}
