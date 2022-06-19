package io.omnika.services.management.service;

import io.omnika.common.exceptions.ObjectNotFoundException;
import io.omnika.common.rest.services.management.dto.TenantDto;
import io.omnika.services.management.converters.TenantConverter;
import io.omnika.services.management.core.service.TenantService;
import io.omnika.services.management.model.Tenant;
import io.omnika.services.management.repository.TenantRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantConverter tenantConverter;

    @Override
    public TenantDto create(TenantDto tenantDto) {
        Tenant toSave = tenantConverter.toDomain(tenantDto);

        return tenantConverter.toDto(tenantRepository.save(toSave));
    }

    @Override
    @Transactional(readOnly = true)
    public TenantDto get(UUID tenantId) {
        return tenantConverter.toDto(
                tenantRepository.findById(tenantId).orElseThrow(() -> new ObjectNotFoundException(tenantId, Tenant.class))
        );
    }
}
