package io.omnika.services.management.converters;

import io.omnika.common.rest.services.management.dto.TenantDto;
import io.omnika.services.management.converters.mappers.TenantMapper;
import io.omnika.services.management.model.Tenant;
import org.springframework.stereotype.Component;

@Component
public class TenantConverter extends AbstractBasicConverter<Tenant, TenantDto> {

    public TenantConverter() {
        super(TenantMapper.INSTANCE);
    }
}
