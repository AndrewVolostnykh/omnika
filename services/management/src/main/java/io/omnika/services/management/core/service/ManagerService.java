package io.omnika.services.management.core.service;

import io.omnika.common.rest.services.management.dto.CreateManagerDto;
import io.omnika.common.rest.services.management.dto.ManagerDto;

public interface ManagerService {
    ManagerDto createManager(CreateManagerDto createManagerDto);
}
