package io.omnika.services.management.web.controller;

import io.omnika.common.rest.services.management.ManagerController;
import io.omnika.common.rest.services.management.dto.CreateManagerDto;
import io.omnika.services.management.core.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ManagerControllerImpl implements ManagerController {

    private final ManagerService managerService;

    @Override
    public void createManager(CreateManagerDto createManagerDto) {
        managerService.createManager(createManagerDto);
    }
}
