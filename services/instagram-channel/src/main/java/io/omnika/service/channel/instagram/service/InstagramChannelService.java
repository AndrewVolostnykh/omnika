package io.omnika.service.channel.instagram.service;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.models.direct.IGThread;
import com.github.instagram4j.instagram4j.models.direct.Inbox;
import com.github.instagram4j.instagram4j.models.direct.item.ThreadItem;
import com.github.instagram4j.instagram4j.models.direct.item.ThreadTextItem;
import com.github.instagram4j.instagram4j.requests.direct.DirectInboxRequest;
import com.github.instagram4j.instagram4j.requests.direct.DirectThreadsBroadcastRequest;
import com.github.instagram4j.instagram4j.requests.direct.DirectThreadsBroadcastRequest.BroadcastTextPayload;
import com.github.instagram4j.instagram4j.requests.direct.DirectThreadsMarkItemSeenRequest;
import io.omnika.common.channel.api.service.ChannelService;
import io.omnika.common.ipc.dto.InboundChannelMessage;
import io.omnika.common.ipc.dto.OutboundChannelMessage;
import io.omnika.common.model.channel.ChannelType;
import io.omnika.common.model.channel.InstagramChannelConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

// TODO: many of users will use instagram two factor authorization
// we have to give it opportunity to users

// TODO: it will be nice to find solution of present problem.
// for now it is no one endpoint to read exactly unseen messages.
// according to it for now we need to store local cache with already received messages
// and send to gateway a huge list of last messages
// TODO: it will be nice to receive only new messages


@Service
@Scope("prototype")
@Slf4j
public class InstagramChannelService extends ChannelService<InstagramChannelConfig> {

    private IGClient igClient;

    @Autowired
    private ExecutorService executorService;
    private CompletableFuture<?> messagesExplorer;

    private final Set<String> localCache = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));

    public InstagramChannelService(UUID tenantId, UUID channelId, InstagramChannelConfig config) throws Exception {
        super(tenantId, channelId, config);
    }

    @Override
    public void init() throws Exception {
        igClient = IGClient.builder()
                .username(config.getUsername())
                .password(config.getPassword())
                .login();
        messagesExplorer = new CompletableFuture<>().completeAsync(() -> {
            discoverMessages();
            return null;
        }, executorService);
    }

    @Override
    protected void sendMessage(OutboundChannelMessage message) throws Exception {
        BroadcastTextPayload broadcastTextPayload = new BroadcastTextPayload(message.getText(), message.getExternalSessionId());
        DirectThreadsBroadcastRequest directThreadsBroadcastRequest = new DirectThreadsBroadcastRequest(broadcastTextPayload);
        igClient.sendRequest(directThreadsBroadcastRequest).exceptionally(exception -> {
            log.error("Exception on sending message to telegram", exception);
            return null;
        });
    }

    private boolean inboxHaveUnreadThreads(Inbox inbox) {
        return inbox.getUnseen_count() != 0; // this condition can produce a problem when somebody checking instagram messages from instagram application but not omnika
    }

    private boolean threadIsUnreadAndPrivate(IGThread igThread) {
        // TODO: investigate for is it needed to use only private threads
        return igThread.getRead_state() != 0 && "private".equals(igThread.getThread_type());
    }

    @SneakyThrows
    private void discoverMessages() {
        while (igClient.isLoggedIn()) {
            executorService.submit(() -> {
                igClient.sendRequest(new DirectInboxRequest().thread_message_limit(10))
                        .thenAccept(response -> {
                            Inbox receivedInbox = response.getInbox();
                            log.warn("Inbox received. Number of threads [{}]", receivedInbox.getThreads().size());
                            if (!inboxHaveUnreadThreads(receivedInbox)) {
                                return;
                            }
                            receivedInbox.getThreads().stream()
                                    .filter(this::threadIsUnreadAndPrivate)
                                    .collect(Collectors.toMap(IGThread::getThread_id, IGThread::getItems))
                                    .forEach((key, value) -> value.stream()
                                            .filter(this::itemReceivedAndHaveTextType)
                                            .forEach(item -> {
                                                igClient.sendRequest(
                                                        new DirectThreadsMarkItemSeenRequest(
                                                                key,
                                                                item.getItem_id()));
                                                log.warn("NEW MESSAGE RECEIVED!!! [{}]", ((ThreadTextItem) item).getText());
                                                handleNewMessage(key, (ThreadTextItem) item);
                                            }));
                        });
            });
            // FIXME: first problem is a presence of hardcoded time to update.
            // FIXME: Second problem that it is an infinite loop. Use MQTT library to fix it
            Thread.sleep(5000);
        }
    }

    private void handleNewMessage(String key, ThreadTextItem item) {
        InboundChannelMessage message = InboundChannelMessage.builder()
                .id(item.getItem_id())
                .sessionId(key)
                .text(item.getText())
                .build();
        onNewMessage(message);
        // localCache.add(item.getItem_id());
    }

    // FIXME: temporary. For now we receiving only text, but in future it should be media also (but for now only text)
    private boolean itemReceivedAndHaveTextType(ThreadItem threadItem) {
        return "text".equals(threadItem.getItem_type())
                && !((boolean) threadItem.getExtraProperties().get("is_sent_by_viewer"))
                && !localCache.contains(threadItem.getItem_id());
    }

    public void stop() {
        // mb it will be nice just to set isChannelEnabled to false?
        // and make new method for interrupting explorer
        messagesExplorer.cancel(true);
    }

    @Override
    public ChannelType getType() {
        return ChannelType.INSTAGRAM;
    }

}
