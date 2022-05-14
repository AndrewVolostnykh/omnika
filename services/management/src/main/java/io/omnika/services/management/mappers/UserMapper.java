package io.omnika.services.management.mappers;

import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.services.management.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "id", target = "userId")
    UserDto userToDto(User user);
}
