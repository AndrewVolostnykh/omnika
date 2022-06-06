package io.omnika.services.management.web.controller;

import io.omnika.common.rest.services.management.TelegramBotChannelController;
import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelDto;
import io.omnika.services.management.core.service.TelegramChannelBotService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TelegramBotChannelControllerImpl implements TelegramBotChannelController {

    private final TelegramChannelBotService telegramBotChannelService;

    @Override
    public TelegramBotChannelDto create(@Valid TelegramBotChannelDto telegramBotChannelDto) {
        return telegramBotChannelService.create(telegramBotChannelDto);
    }
}
