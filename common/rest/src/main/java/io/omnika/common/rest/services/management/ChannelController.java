package io.omnika.common.rest.services.management;

import io.omnika.common.model.channel.Channel;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channel")
public interface ChannelController {

    @PostMapping
    @PreAuthorize("hasAuthority('TENANT_ADMIN')")
    Channel createChannel(@RequestBody Channel channel);

    @PutMapping
    @PreAuthorize("hasAuthority('TENANT_ADMIN')")
    Channel updateChannel(@RequestBody Channel channel);

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'MANAGER')")
    List<Channel> listChannels();

}
