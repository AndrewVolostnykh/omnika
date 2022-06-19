package io.omnika.services.management.converters;

import io.omnika.common.core.converters.AbstractBasicEntityConverter;
import io.omnika.common.rest.services.management.dto.TenantDto;
import io.omnika.services.management.converters.mappers.TenantMapper;
import io.omnika.services.management.model.Tenant;
import org.springframework.stereotype.Component;

@Component
public class TenantConverter extends AbstractBasicEntityConverter<Tenant, TenantDto> {

    public TenantConverter() {
        super(TenantMapper.INSTANCE);
    }
}
