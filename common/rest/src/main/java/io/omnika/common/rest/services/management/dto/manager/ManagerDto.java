package io.omnika.common.rest.services.management.dto.manager;

import io.omnika.common.rest.services.management.dto.TenantDto;
import io.omnika.common.rest.services.management.dto.UserDto;
import lombok.Data;

@Data
public class ManagerDto {

    private String name;
    private UserDto user;
    private TenantDto tenant;

}
