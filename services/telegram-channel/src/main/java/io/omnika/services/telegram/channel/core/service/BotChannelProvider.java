package io.omnika.services.telegram.channel.core.service;

import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelDto;
import java.util.List;

public interface BotChannelProvider {

    void createBot(TelegramBotChannelDto telegramBotChannelDto);

    // TODO: to remove
    List<TelegramBotChannelDto> list();

}
