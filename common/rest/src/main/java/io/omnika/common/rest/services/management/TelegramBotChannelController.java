package io.omnika.common.rest.services.management;

import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channel/telegram")
@PreAuthorize("isAuthenticated()")
public interface TelegramBotChannelController {

    @PostMapping
    TelegramBotChannelDto create(@RequestBody TelegramBotChannelDto telegramBotChannelDto);

}
