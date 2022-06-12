package io.omnika.services.management.converters.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.rest.services.management.dto.manager.ManagerDto;
import io.omnika.services.management.model.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ManagerMapper extends BasicEntityMapper<Manager, ManagerDto> {

    ManagerMapper INSTANCE = Mappers.getMapper(ManagerMapper.class);
}
