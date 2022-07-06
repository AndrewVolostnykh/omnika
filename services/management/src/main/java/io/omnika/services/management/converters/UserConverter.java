package io.omnika.services.management.converters;

import io.omnika.common.core.converters.AbstractBasicEntityConverter;
import io.omnika.common.rest.services.management.dto.User;
import io.omnika.services.management.converters.mappers.UserMapper;
import io.omnika.services.management.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends AbstractBasicEntityConverter<UserEntity, User> {

    public UserConverter() {
        super(UserMapper.INSTANCE);
    }

}
