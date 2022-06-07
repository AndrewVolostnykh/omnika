package io.omnika.common.rest.services.management;

import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channel")
@PreAuthorize("isAuthenticated()")
public interface ChannelsController {

    // TODO: think about table view repository here
    // because there is a problem with pagination
    // as first problem we will meet
    @GetMapping
    List<ChannelDto> list();
}
