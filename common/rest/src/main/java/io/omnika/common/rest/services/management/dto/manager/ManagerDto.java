package io.omnika.common.rest.services.management.dto.manager;

import lombok.Data;

@Data
public class ManagerDto {

    private Long id;
    private String name;
    // FIXME: need to investigate how to query manager with user information without StackOverflowError
//    private UserDto user;
//    private TenantDto tenant;

}
