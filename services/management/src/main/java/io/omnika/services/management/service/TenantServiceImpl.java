package io.omnika.services.management.service;

import io.omnika.common.exceptions.ObjectNotFoundException;
import io.omnika.common.rest.services.management.dto.Tenant;
import io.omnika.services.management.converters.TenantConverter;
import io.omnika.services.management.core.service.TenantService;
import io.omnika.services.management.model.TenantEntity;
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
    public Tenant create(Tenant tenant) {
        TenantEntity toSave = tenantConverter.toDomain(tenant);

        return tenantConverter.toDto(tenantRepository.save(toSave));
    }

    @Override
    @Transactional(readOnly = true)
    public Tenant get(UUID tenantId) {
        return tenantConverter.toDto(
                tenantRepository.findById(tenantId).orElseThrow(() -> new ObjectNotFoundException(tenantId, TenantEntity.class))
        );
    }


}
