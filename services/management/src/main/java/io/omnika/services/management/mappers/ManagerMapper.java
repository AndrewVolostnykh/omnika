package io.omnika.services.management.mappers;

import io.omnika.common.rest.services.management.dto.ManagerDto;
import io.omnika.services.management.model.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ManagerMapper {

    ManagerMapper INSTANCE = Mappers.getMapper(ManagerMapper.class);

    ManagerDto managerToDto(Manager manager);

}
