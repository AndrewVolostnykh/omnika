package io.omnika.services.management.mappers;

import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.services.management.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToDto(User user);

    User userDtoToDomain(UserDto userDto);
}
