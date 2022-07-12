package io.omnika.services.messaging.gateway.service.messaging;

import io.omnika.common.model.channel.ChannelSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChannelSessionAccessControlService {

    private Map<UUID, List<UUID>> channelsToAdmittedUsers;

    public List<UUID> getAdmittedUsers(ChannelSession channelSession) {
        return List.of(UUID.fromString("7f32c152-9a8d-4c1b-9836-23a589518e15"));
    }
    
}
