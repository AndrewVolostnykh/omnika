package io.omnika.services.management.converters.mappers;

import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.services.management.core.converter.mapper.BasicMapper;
import io.omnika.services.management.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends BasicMapper<User, UserDto> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
