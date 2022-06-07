package io.omnika.services.management.core.service;

import io.omnika.common.rest.services.management.dto.manager.CreateManagerDto;
import io.omnika.common.rest.services.management.dto.manager.ManagerDto;

public interface ManagerService {
    ManagerDto createManager(CreateManagerDto createManagerDto);
}
