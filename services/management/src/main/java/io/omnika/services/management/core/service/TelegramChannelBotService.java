package io.omnika.services.management.core.service;

import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelDto;

public interface TelegramChannelBotService {

    TelegramBotChannelDto create(TelegramBotChannelDto telegramBotChannelDto);

}
