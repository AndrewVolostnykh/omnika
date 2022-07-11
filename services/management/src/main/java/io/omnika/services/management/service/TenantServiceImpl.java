package io.omnika.services.management.service;

import io.omnika.common.exceptions.ObjectNotFoundException;
import io.omnika.common.rest.services.management.dto.Tenant;
import io.omnika.services.management.core.service.TenantService;
import io.omnika.services.management.mappers.TenantMapper;
import io.omnika.services.management.model.TenantEntity;
import io.omnika.services.management.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

    @Override
    public Tenant createTenant(Tenant tenant) {
        tenant.setId(null);
        TenantEntity toSave = tenantMapper.toDomain(tenant);
        return tenantMapper.toDto(tenantRepository.save(toSave));
    }

    @Override
    public Tenant updateTenant(Tenant tenant) {
        if (!tenantRepository.existsById(tenant.getId())) {
            throw new ObjectNotFoundException(tenant.getId(), TenantEntity.class);
        }
        TenantEntity tenantEntity = tenantMapper.toDomain(tenant);
        tenantEntity = tenantRepository.save(tenantEntity);
        return tenantMapper.toDto(tenantEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Tenant getTenantById(UUID id) {
        return tenantMapper.toDto(
                tenantRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, TenantEntity.class))
        );
    }

    @Override
    public List<Tenant> getTenants() {
        return tenantRepository.findAll().stream()
                .map(tenantMapper::toDto)
                .collect(Collectors.toList());
    }

}
