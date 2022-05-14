package io.omnika.common.security.model;

import java.util.List;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class AccountPrincipal {
    private Long userId;
    private String email;
    private List<GrantedAuthority> authorities;
    private String token;
}
