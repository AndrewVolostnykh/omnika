package io.omnika.common.security.model;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class UserPrincipal {
    private UUID userId;
    private String email;
    private List<GrantedAuthority> authorities;
    private String token;
    private UUID tenantId;
}
