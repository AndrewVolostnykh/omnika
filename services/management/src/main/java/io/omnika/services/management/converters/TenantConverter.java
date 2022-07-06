package io.omnika.services.management.converters;

import io.omnika.common.core.converters.AbstractBasicEntityConverter;
import io.omnika.common.rest.services.management.dto.Tenant;
import io.omnika.services.management.converters.mappers.TenantMapper;
import io.omnika.services.management.model.TenantEntity;
import org.springframework.stereotype.Component;

@Component
public class TenantConverter extends AbstractBasicEntityConverter<TenantEntity, Tenant> {

    public TenantConverter() {
        super(TenantMapper.INSTANCE);
    }
}
