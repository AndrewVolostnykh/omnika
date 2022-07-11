package io.omnika.services.messaging.gateway.mappers;

import io.omnika.common.core.converters.BasicEntityMapper;
import io.omnika.common.model.channel.Sender;
import io.omnika.services.messaging.gateway.model.SenderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SenderMapper extends BasicEntityMapper<SenderEntity, Sender> {

}
