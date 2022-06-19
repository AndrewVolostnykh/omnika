package io.omnika.services.telegram.channel.core.service;

import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import java.util.List;

public interface BotChannelProvider {

    void createBot(ChannelDto channelDto);

    // TODO: to remove
    List<ChannelDto> list();

}
