package io.omnika.services.management.service;

import io.omnika.common.exceptions.ExceptionCodes.Auth;
import io.omnika.common.exceptions.ExceptionCodes.Validation;
import io.omnika.common.exceptions.FieldError;
import io.omnika.common.exceptions.ObjectNotFoundException;
import io.omnika.common.exceptions.ValidationException;
import io.omnika.common.exceptions.auth.IncorrectPasswordException;
import io.omnika.common.exceptions.auth.UserNotFoundException;
import io.omnika.common.rest.services.management.dto.Tenant;
import io.omnika.common.rest.services.management.dto.User;
import io.omnika.common.rest.services.management.dto.auth.SetPasswordDto;
import io.omnika.common.rest.services.management.dto.auth.SignUpDto;
import io.omnika.common.rest.services.management.dto.auth.SigningDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.omnika.common.rest.services.management.dto.manager.CreateManagerDto;
import io.omnika.common.security.model.Authority;
import io.omnika.common.security.model.UserPrincipal;
import io.omnika.common.security.utils.AuthenticationHelper;
import io.omnika.services.management.converters.UserConverter;
import io.omnika.services.management.core.service.TenantService;
import io.omnika.services.management.core.service.UserService;
import io.omnika.services.management.model.TenantEntity;
import io.omnika.services.management.model.UserEntity;
import io.omnika.services.management.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;
    private final TenantService tenantService;

    @Override
    public void signUp(SignUpDto signUpDto) {
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            // FIXME: remove this code duplication. U can use custom validators for checking
            // email for uniq
            throw new ValidationException(FieldError.builder().field("email").code(Validation.NOT_UNIQUE).build());
        }

        Tenant tenant = new Tenant();
        tenant.setName(signUpDto.getTenantName());
        tenant = tenantService.create(tenant);

        UserEntity user = new UserEntity();
        user.setEmail(signUpDto.getEmail());
        user.setAuthority(Authority.TENANT_ADMIN);
        user.setActive(false);
        user.setActivationToken(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setTenant(new TenantEntity(tenant.getId()));
        userRepository.save(user);

        // here will be sent activation link
        log.warn("Activation of tenant admin [{}]. Use this token: [{}]", signUpDto.getEmail(), user.getActivationToken());

//        return new TokenDto(tokenService.createToken(userRepository.save(user)));
    }

    @Override
    public void signUpManager(UUID tenantId, CreateManagerDto createManagerDto) {
        // FIXME: remove this code duplication. U can use custom validators for checking
        // email for uniq
        if (userRepository.existsByEmail(createManagerDto.getEmail())) {
            throw new ValidationException(FieldError.builder().field("email").code(Validation.NOT_UNIQUE).build());
        }

        UserEntity user = new UserEntity();
        user.setEmail(createManagerDto.getEmail());
        user.setActive(false);
        user.setTenant(new TenantEntity(tenantId));
        user.setAuthority(Authority.MANAGER);
        user.setName(createManagerDto.getName());
        user.setActivationToken(UUID.randomUUID());

        userRepository.save(user);

        // here will be sent password setting link
        log.warn("Activating manager [{}]. User this token to activate and set pass [{}]", user.getEmail(), user.getActivationToken());
    }

    @Override
    public TokenDto login(SigningDto signingDto) {
        UserEntity user = userRepository.findByEmail(signingDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!user.isActive()) {
            // FIXME: it should not be validation, but authentication
            throw new ValidationException(Auth.USER_NOT_ACTIVE);
        }

        if (passwordEncoder.matches(signingDto.getPassword(), user.getPassword())) {
            return new TokenDto(tokenService.createToken(user));
        } else {
            throw new IncorrectPasswordException();
        }
    }

    @Override
    public TokenDto activate(UUID activationToken) {
        UserEntity user = userRepository.findByActivationToken(activationToken).orElseThrow(UserNotFoundException::new);
        user.setActive(true);
        user = userRepository.save(user);

        return new TokenDto(tokenService.createToken(user));
    }

    @Override
    public TokenDto setPassword(UUID activationToken, SetPasswordDto setPasswordDto) {
        UserEntity user = userRepository.findByActivationToken(activationToken).orElseThrow(UserNotFoundException::new);

        // validation for can user set password if it allready present
        // I mean on tenant activation we sending link with only token,
        // but what if user will use endpoint for token and password?

        user.setPassword(passwordEncoder.encode(setPasswordDto.getPassword()));
        user.setActive(true);
        user.setActivationToken(null);

        return new TokenDto(tokenService.createToken(userRepository.save(user)));
    }

//    @Override
//    public UserDto get(Long id) {
//        return userConverter.toDto(
//                userRepository.findById(id)
//                        .orElseThrow(() -> new ObjectNotFoundException(id, User.class))
//        );
//    }
//
    @Override
    public User getCurrent() {
        UserPrincipal userPrincipal = AuthenticationHelper.getAuthenticationDetails();

        return userRepository.findById(userPrincipal.getUserId())
                .map(userConverter::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(userPrincipal.getUserId(), UserEntity.class));
    }
}
