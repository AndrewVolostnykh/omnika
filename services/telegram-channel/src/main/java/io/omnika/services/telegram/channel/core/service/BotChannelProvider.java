package io.omnika.services.telegram.channel.core.service;

import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.services.telegram.channel.service.BotChannelService;
import java.util.List;
import java.util.UUID;

public interface BotChannelProvider {

    void createBot(ChannelDto channelDto);

    // TODO: to remove
    List<ChannelDto> list();

}
