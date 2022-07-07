package io.omnika.services.messaging.gateway.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.services.messaging.gateway.model.SenderEntity;
import org.apache.kafka.clients.producer.internals.Sender;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SenderMapper extends BasicEntityMapper<SenderEntity, Sender> {

}
