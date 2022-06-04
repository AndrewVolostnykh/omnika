package io.omnika.services.management.service;

import io.omnika.common.exceptions.ExceptionCodes.Auth;
import io.omnika.common.exceptions.ExceptionCodes.Validation;
import io.omnika.common.exceptions.FieldError;
import io.omnika.common.exceptions.ObjectNotFoundException;
import io.omnika.common.exceptions.ValidationException;
import io.omnika.common.exceptions.auth.IncorrectPasswordException;
import io.omnika.common.exceptions.auth.UserNotFoundException;
import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.common.rest.services.management.dto.auth.SetPasswordDto;
import io.omnika.common.rest.services.management.dto.auth.SigningDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.omnika.common.security.model.Authority;
import io.omnika.common.security.model.UserPrincipal;
import io.omnika.common.security.utils.AuthenticationHelper;
import io.omnika.services.management.converters.UserConverter;
import io.omnika.services.management.core.service.UserService;
import io.omnika.services.management.converters.mappers.UserMapper;
import io.omnika.services.management.model.User;
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

    @Override
    public TokenDto signUp(SigningDto signingDto) {
        if (userRepository.existsByEmail(signingDto.getEmail())) {
            throw new ValidationException(FieldError.builder().field("email").code(Validation.NOT_UNIQUE).build());
        }

        User user = new User();
        user.setEmail(signingDto.getEmail());
        user.setAuthority(Authority.USER);
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(signingDto.getPassword()));

        return TokenDto.builder().authToken(tokenService.createToken(userRepository.save(user))).build();
    }

    @Override
    public UserDto createNeedActivation(String email) {
        User user = new User();
        user.setEmail(email);
        user.setActive(false);
        user.setActivationToken(UUID.randomUUID());
        user.setAuthority(Authority.USER);

        // TODO: there sending email
        log.warn("User to activate: email [{}], token [{}]", user.getEmail(), user.getActivationToken());

        return userConverter.toDto(userRepository.save(user));
    }

    @Override
    public TokenDto login(SigningDto signingDto) {
        User user = userRepository.findByEmail(signingDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!user.isActive()) {
            // it should not be validation, but authentication
            throw new ValidationException(Auth.USER_NOT_ACTIVE);
        }

        if (passwordEncoder.matches(signingDto.getPassword(), user.getPassword())) {
            return TokenDto.builder().authToken(tokenService.createToken(user)).build();
        } else {
            throw new IncorrectPasswordException();
        }
    }

    public TokenDto activate(UUID activationToken, SetPasswordDto setPasswordDto) {
        User user = userRepository.findByActivationToken(activationToken).orElseThrow(UserNotFoundException::new);

        user.setPassword(passwordEncoder.encode(setPasswordDto.getPassword()));
        user.setActive(true);
        user.setActivationToken(null);

        return TokenDto.builder().authToken(tokenService.createToken(userRepository.save(userRepository.save(user)))).build();
    }

    @Override
    public UserDto get(Long id) {
        return userConverter.toDto(
                userRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(id, User.class))
        );
    }

    @Override
    public UserDto getCurrent() {
        UserPrincipal userPrincipal = AuthenticationHelper.getAuthenticationDetails();

        return userRepository.findById(userPrincipal.getUserId())
                .map(userConverter::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(userPrincipal.getUserId(), User.class));
    }
}
