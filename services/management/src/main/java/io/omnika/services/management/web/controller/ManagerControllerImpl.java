package io.omnika.services.management.web.controller;

import io.omnika.common.rest.services.management.ManagerController;
import io.omnika.common.rest.services.management.dto.manager.CreateManagerDto;
import io.omnika.common.rest.services.management.dto.manager.ManagerDto;
import io.omnika.services.management.core.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManagerControllerImpl implements ManagerController {

    private final ManagerService managerService;

    @Override
    public ManagerDto createManager(CreateManagerDto createManagerDto) {
        return managerService.createManager(createManagerDto);
    }
}
