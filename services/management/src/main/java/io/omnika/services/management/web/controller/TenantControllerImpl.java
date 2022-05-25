package io.omnika.services.management.web.controller;

import io.omnika.common.rest.controller.BaseController;
import io.omnika.common.rest.services.management.TenantController;
import io.omnika.common.rest.services.management.dto.TenantDto;
import io.omnika.services.management.core.service.TenantService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TenantControllerImpl extends BaseController implements TenantController {

    private final TenantService tenantService;

    public TenantDto create(@Valid TenantDto tenantDto) {
        return tenantService.create(tenantDto);
    }

    public TenantDto update(@Valid TenantDto tenantDto) {
        return tenantService.update(tenantDto);
    }

    public List<TenantDto> list() {
        return tenantService.list();
    }

    public TenantDto get(Long tenantId) {
        return tenantService.get(tenantId);
    }

}
