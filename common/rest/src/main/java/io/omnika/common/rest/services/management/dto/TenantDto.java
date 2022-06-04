package io.omnika.common.rest.services.management.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class TenantDto {

    private Long id;
    @NotNull
    @NotBlank
    @Size(min = 3)
    private String name;
    private UserDto user;
    private List<ManagerDto> managers;

}
