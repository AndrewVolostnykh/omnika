package io.omnika.services.messaging.gateway.web.controller;

import io.omnika.common.model.channel.ChannelSession;
import io.omnika.common.rest.controller.BaseController;
import io.omnika.services.messaging.gateway.core.service.ChannelSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class ChannelSessionController extends BaseController {

    private final ChannelSessionService channelSessionService;

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public List<ChannelSession> listChannelSessions() {
        return channelSessionService.findChannelSessionsByTenantId(getTenantId());
    }

}
