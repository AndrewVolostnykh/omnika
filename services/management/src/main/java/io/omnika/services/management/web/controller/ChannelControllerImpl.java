package io.omnika.services.management.web.controller;

import io.omnika.common.rest.controller.BaseController;
import io.omnika.common.rest.services.management.ChannelController;
import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.services.management.core.service.ChannelService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChannelControllerImpl extends BaseController implements ChannelController {

    private final ChannelService channelService;

    @Override
    @PreAuthorize("hasAuthority('TENANT_ADMIN')")
    public ChannelDto create(@Valid ChannelDto channelDto) {
        return channelService.create(getPrincipal().getTenantId(), channelDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'MANAGER')")
    public List<ChannelDto> list() {
        return channelService.list(getPrincipal().getTenantId());
    }

    //    // FIXME: remove
//    @PostMapping("/new/chat")
//    public void produceNewTelegramChat(@RequestBody TelegramBotChannelConfig telegramBotChannelDto) {
////        telegramBotChannelService.produceNewChannel(telegramBotChannelDto);
//    }

}
