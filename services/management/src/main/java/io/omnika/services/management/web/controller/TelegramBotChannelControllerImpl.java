package io.omnika.services.management.web.controller;

import io.omnika.common.rest.services.management.TelegramBotChannelController;
import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelDto;
import io.omnika.services.management.service.TelegramBotChannelServiceImpl;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TelegramBotChannelControllerImpl implements TelegramBotChannelController {

    private final TelegramBotChannelServiceImpl telegramBotChannelService;
    private final DiscoveryClient discoveryClient;

    @Override
    public TelegramBotChannelDto create(@Valid TelegramBotChannelDto telegramBotChannelDto) {
        return telegramBotChannelService.create(telegramBotChannelDto);
    }

    // FIXME: remove
    @PostMapping("/new/chat")
    public void produceNewTelegramChat(@RequestBody TelegramBotChannelDto telegramBotChannelDto) {
        telegramBotChannelService.produceNewChannel(telegramBotChannelDto);
    }
}
