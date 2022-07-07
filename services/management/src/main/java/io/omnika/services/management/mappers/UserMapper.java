package io.omnika.services.management.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.rest.services.management.dto.User;
import io.omnika.services.management.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper extends BasicEntityMapper<UserEntity, User> {

    @Override
    @Mapping(target = "tenantId", expression = "java(userEntity.getTenant().getId())")
    User toDto(UserEntity userEntity);

}
