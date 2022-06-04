package io.omnika.common.rest.services.management;

import io.omnika.common.rest.services.management.dto.manager.CreateManagerDto;
import io.omnika.common.rest.services.management.dto.manager.ManagerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Manager controller", description = "CRUDL for manager entity")
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/manager")
public interface ManagerController {

    @Operation(description = "Creating manager for exact tenant. Method returns a pattern for link to activate user: set password")
    @PostMapping
    ManagerDto createManager(@RequestBody CreateManagerDto createManagerDto);
}
