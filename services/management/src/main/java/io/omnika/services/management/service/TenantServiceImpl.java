package io.omnika.services.management.service;

import io.omnika.common.exceptions.ExceptionCodes;
import io.omnika.common.exceptions.FieldError;
import io.omnika.common.exceptions.ObjectNotFoundException;
import io.omnika.common.exceptions.ValidationException;
import io.omnika.common.rest.services.management.dto.TenantDto;
import io.omnika.common.security.utils.AuthenticationHelper;
import io.omnika.services.management.core.service.TenantService;
import io.omnika.services.management.mappers.TenantMapper;
import io.omnika.services.management.model.Tenant;
import io.omnika.services.management.model.User;
import io.omnika.services.management.repository.TenantRepository;
import io.omnika.services.management.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;

    @Override
    public TenantDto create(TenantDto tenantDto) {
        Long currentUserId = AuthenticationHelper.getAuthenticationDetails().getUserId();

        if (tenantDto.getId() != null && tenantRepository.existsById(tenantDto.getId())) {
            throw new IllegalArgumentException("Tenant already present");
        }

        if (tenantRepository.existsByNameAndUserId(tenantDto.getName(), currentUserId)) {
            throw new ValidationException(
                    FieldError.builder()
                            .field("name")
                            .code(ExceptionCodes.Validation.NOT_UNIQUE)
                            .build()
            );
        }

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ObjectNotFoundException(currentUserId, User.class));
        Tenant toSave = TenantMapper.INSTANCE.tenantDtoToDomain(tenantDto);
        toSave.setUser(currentUser);

        Tenant saved = tenantRepository.save(toSave);

        return TenantMapper.INSTANCE.tenantToDto(saved);
    }

    @Override
    public TenantDto update(TenantDto tenantDto) {
        if (tenantDto.getId() == null) {
            throw new IllegalArgumentException("To update id of tenant should be specified");
        }

        Tenant updated = tenantRepository.save(TenantMapper.INSTANCE.tenantDtoToDomain(tenantDto));

        return TenantMapper.INSTANCE.tenantToDto(updated);
    }

    @Override
    public List<TenantDto> list() {
        return tenantRepository.findByUserId(AuthenticationHelper.getAuthenticationDetails().getUserId())
                .stream()
                .map(TenantMapper.INSTANCE::tenantToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TenantDto get(Long tenantId) {
        return TenantMapper.INSTANCE.tenantToDto(
                tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ObjectNotFoundException(tenantId, Tenant.class))
        );
    }
}
