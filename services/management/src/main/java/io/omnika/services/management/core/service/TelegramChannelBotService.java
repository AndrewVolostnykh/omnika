package io.omnika.services.management.core.service;

import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelConfig;

public interface TelegramChannelBotService {

    TelegramBotChannelConfig create(TelegramBotChannelConfig telegramBotChannelDto);

}
