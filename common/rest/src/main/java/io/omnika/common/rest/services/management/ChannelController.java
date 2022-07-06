package io.omnika.common.rest.services.management;

import io.omnika.common.model.channel.Channel;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channel")
@PreAuthorize("isAuthenticated()")
public interface ChannelController {

    @PostMapping
    Channel create(@RequestBody Channel channel);

    @GetMapping("/list")
    List<Channel> listChannels();

}
