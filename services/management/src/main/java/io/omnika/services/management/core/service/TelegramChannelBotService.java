package io.omnika.services.management.core.service;

import io.omnika.common.model.channel.TelegramBotChannelConfig;

public interface TelegramChannelBotService {

    TelegramBotChannelConfig create(TelegramBotChannelConfig telegramBotChannelDto);

}
