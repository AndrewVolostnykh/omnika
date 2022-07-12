package io.omnika.services.messaging.gateway.service.messaging;

import io.omnika.common.model.channel.ChannelMessage;
import io.omnika.common.model.channel.ChannelSession;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChannelMessageSubscriptionService {

    private final ChannelSessionAccessControlService accessControlService;
    private final SimpMessagingTemplate messagingTemplate;

    public void handleNewMessage(ChannelSession session, ChannelMessage message) {
        List<UUID> admittedUsers = accessControlService.getAdmittedUsers(session);
        admittedUsers.forEach(userId -> {
            messagingTemplate.convertAndSend("/user/" + userId.toString() + "/messages/session/" + session.getId(), message);
        });
    }

}
