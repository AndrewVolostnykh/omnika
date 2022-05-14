package io.omnika.services.management.service;

import io.omnika.common.exception.ExceptionCodes.Auth;
import io.omnika.common.exception.FieldError;
import io.omnika.common.exception.exceptions.ValidationException;
import io.omnika.common.exception.exceptions.auth.IncorrectPasswordException;
import io.omnika.common.exception.exceptions.auth.UserNotFoundException;
import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.common.rest.services.management.dto.auth.SigningDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.omnika.common.security.model.Authority;
import io.omnika.services.management.core.service.UserService;
import io.omnika.services.management.mappers.UserMapper;
import io.omnika.services.management.model.User;
import io.omnika.services.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto create(SigningDto signingDto) {
        if (userRepository.existsByEmail(signingDto.getEmail())) {
            throw new ValidationException(FieldError.builder().field("email").code(Auth.EMAIL_NOT_UNIQ).build());
        }

        User user = new User();
        user.setEmail(signingDto.getEmail());
        user.setAuthority(Authority.USER);
        user.setPassword(passwordEncoder.encode(signingDto.getPassword()));

        userRepository.save(user);
        return UserMapper.INSTANCE.userToDto(user);
    }

    @Override
    public TokenDto login(SigningDto signingDto) {
        User user = userRepository.findByEmail(signingDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(signingDto.getPassword(), user.getPassword())) {
            return TokenDto.builder().authToken(tokenService.createToken(user)).build();
        } else {
            throw new IncorrectPasswordException();
        }
    }
}
