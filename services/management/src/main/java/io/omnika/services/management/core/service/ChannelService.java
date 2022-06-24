package io.omnika.services.management.core.service;

import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.common.rest.services.management.model.ChannelType;
import java.util.List;
import java.util.UUID;

public interface ChannelService {

    ChannelDto create(UUID tenantId, ChannelDto channelDto);

    List<ChannelDto> list(UUID tenantId);

//    List<ChannelDto> listChannelsToServices(ChannelType channelType);

}
