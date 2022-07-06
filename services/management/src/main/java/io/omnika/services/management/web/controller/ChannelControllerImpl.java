package io.omnika.services.management.web.controller;

import io.omnika.common.model.channel.Channel;
import io.omnika.common.rest.controller.BaseController;
import io.omnika.common.rest.services.management.ChannelController;
import io.omnika.services.management.core.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChannelControllerImpl extends BaseController implements ChannelController {

    private final ChannelService channelService;

    @Override
    @PreAuthorize("hasAuthority('TENANT_ADMIN')")
    public Channel create(@Valid Channel channel) {
        return channelService.create(getTenantId(), channel);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'MANAGER')")
    public List<Channel> listChannels() {
        return channelService.getChannelsByTenantId(getTenantId());
    }

    //    // FIXME: remove
//    @PostMapping("/new/chat")
//    public void produceNewTelegramChat(@RequestBody TelegramBotChannelConfig telegramBotChannelDto) {
////        telegramBotChannelService.produceNewChannel(telegramBotChannelDto);
//    }

}
