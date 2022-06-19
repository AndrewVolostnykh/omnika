package io.omnika.common.rest.services.management;

import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
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
    ChannelDto create(@RequestBody ChannelDto channelDto);

    @GetMapping("/list")
    List<ChannelDto> list();

}
