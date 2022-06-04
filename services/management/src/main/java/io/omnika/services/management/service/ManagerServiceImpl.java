package io.omnika.services.management.service;

import io.omnika.common.exceptions.ExceptionCodes.Validation;
import io.omnika.common.exceptions.ObjectNotFoundException;
import io.omnika.common.exceptions.ValidationException;
import io.omnika.common.rest.services.management.dto.CreateManagerDto;
import io.omnika.common.rest.services.management.dto.ManagerDto;
import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.common.security.model.UserPrincipal;
import io.omnika.common.security.utils.AuthenticationHelper;
import io.omnika.services.management.core.service.ManagerService;
import io.omnika.services.management.core.service.UserService;
import io.omnika.services.management.mappers.ManagerMapper;
import io.omnika.services.management.mappers.UserMapper;
import io.omnika.services.management.model.Manager;
import io.omnika.services.management.model.Tenant;
import io.omnika.services.management.model.User;
import io.omnika.services.management.repository.ManagerRepository;
import io.omnika.services.management.repository.TenantRepository;
import io.omnika.services.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final ManagerRepository managerRepository;

    @Override
    public ManagerDto createManager(CreateManagerDto createManagerDto) {
        UserPrincipal userDetails = AuthenticationHelper.getAuthenticationDetails();

        Tenant tenant = tenantRepository.findById(createManagerDto.getTenantId())
            .orElseThrow(() -> new ObjectNotFoundException(createManagerDto.getTenantId(), Tenant.class));

        if (!userRepository.existsByIdAndTenantsContains(userDetails.getUserId(), tenant)) {
            throw new ValidationException(Validation.NOT_OWNER_CREATES_MANAGER);
        }

        UserDto userDto;

        if (!userRepository.existsByEmail(createManagerDto.getEmail())) {
            userDto = userService.createNeedActivation(createManagerDto.getEmail());
        } else {
            if (userDetails.getEmail().equals(createManagerDto.getEmail())) {
                throw new ValidationException(Validation.OWNER_CANNOT_BE_MANAGER);
            }

            userDto = UserMapper.INSTANCE.userToDto(userRepository.findByEmail(createManagerDto.getEmail())
                    .orElseThrow(() -> new ObjectNotFoundException(createManagerDto.getEmail(), User.class)));
        }

        Manager manager = new Manager();
        manager.setName(createManagerDto.getName());
        manager.setUser(UserMapper.INSTANCE.userDtoToDomain(userDto));
        manager.setTenant(tenant);

        return ManagerMapper.INSTANCE.managerToDto(managerRepository.save(manager));
    }
}
