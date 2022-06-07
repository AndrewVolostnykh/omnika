package io.omnika.services.management.converters;

import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.services.management.converters.mappers.UserMapper;
import io.omnika.services.management.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends AbstractBasicConverter<User, UserDto> {

    public UserConverter() {
        super(UserMapper.INSTANCE);
    }
}
