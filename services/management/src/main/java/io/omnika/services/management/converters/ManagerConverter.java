package io.omnika.services.management.converters;

import io.omnika.common.core.converters.AbstractBasicEntityConverter;
import io.omnika.common.rest.services.management.dto.manager.ManagerDto;
import io.omnika.services.management.converters.mappers.ManagerMapper;
import io.omnika.services.management.model.Manager;
import org.springframework.stereotype.Component;

@Component
public class ManagerConverter extends AbstractBasicEntityConverter<Manager, ManagerDto> {

    public ManagerConverter() {
        super(ManagerMapper.INSTANCE);
    }
}
