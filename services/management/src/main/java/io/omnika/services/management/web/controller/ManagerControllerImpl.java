package io.omnika.services.management.web.controller;

import io.omnika.common.rest.services.management.ManagerController;
import io.omnika.common.rest.services.management.dto.manager.CreateManagerDto;
import io.omnika.common.rest.services.management.dto.manager.ManagerDto;
import io.omnika.services.management.core.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ManagerControllerImpl implements ManagerController {

    private final ManagerService managerService;

    @Override
    public ManagerDto createManager(CreateManagerDto createManagerDto) {
        return managerService.createManager(createManagerDto);
    }
}
