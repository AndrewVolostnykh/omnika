package io.omnika.service.instagram.channel.service;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.models.direct.IGThread;
import com.github.instagram4j.instagram4j.models.direct.Inbox;
import com.github.instagram4j.instagram4j.models.direct.item.ThreadItem;
import com.github.instagram4j.instagram4j.models.direct.item.ThreadTextItem;
import com.github.instagram4j.instagram4j.requests.direct.DirectInboxRequest;
import com.github.instagram4j.instagram4j.requests.direct.DirectThreadsBroadcastRequest;
import com.github.instagram4j.instagram4j.requests.direct.DirectThreadsBroadcastRequest.BroadcastTextPayload;
import com.github.instagram4j.instagram4j.requests.direct.DirectThreadsMarkItemSeenRequest;
import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.common.rest.services.channels.dto.ChannelSessionDto;
import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.common.rest.services.management.dto.channel.InstagramChannelConfig;
import io.omnika.common.rest.services.management.model.ChannelType;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;

// TODO: many of users will use instagram two factor authorization
// we have to give it opportunity to users

// TODO: it will be nice to find solution of present problem.
// for now it is no one endpoint to read exactly unseen messages.
// according to it for now we need to store local cache with already received messages
// and send to gateway a huge list of last messages
// TODO: it will be nice to receive only new messages

@Slf4j
@Setter
public class InstagramChannel {
    private static final String RECEIVED_MESSAGE_EXCHANGE = "message-in-0";

    private final IGClient igClient;
    private final ChannelDto channelDto;
    private final StreamBridge streamBridge;
    private final ExecutorService executorService;
    private boolean isChannelEnabled;
    private CompletableFuture<?> messagesExplorer;

    private final Set<String> localCache = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));

    @SneakyThrows
    public InstagramChannel(ChannelDto channelDto,
            StreamBridge streamBridge,
            boolean isChannelEnabled,
            ExecutorService executorService) {
        InstagramChannelConfig instagramChannelConfig = (InstagramChannelConfig) channelDto.getConfig();
        this.igClient = IGClient.builder()
                .username(instagramChannelConfig.getUsername())
                .password(instagramChannelConfig.getPassword())
                .login();
        this.channelDto = channelDto;
        this.streamBridge = streamBridge;
        this.executorService = executorService;
        this.isChannelEnabled = isChannelEnabled;
    }

    @SneakyThrows
    public void sendMessage(ChannelMessageDto channelMessageDto) {
        BroadcastTextPayload broadcastTextPayload = new BroadcastTextPayload(
                channelMessageDto.getText(),
                channelMessageDto.getChannelSessionDto().getSessionId()
        );

        DirectThreadsBroadcastRequest directThreadsBroadcastRequest = new DirectThreadsBroadcastRequest(broadcastTextPayload);

        igClient.sendRequest(directThreadsBroadcastRequest).exceptionally(exception -> {
            log.error("Exception on sending message to telegram", exception);
            return null;
        });
    }

    public void stop() {
        // mb it will be nice just to set isChannelEnabled to false?
        // and make new method for interrupting explorer
        messagesExplorer.cancel(true);
    }

    public void start() {
        messagesExplorer = new CompletableFuture<>().completeAsync(() -> {discoverMessages(); return null;}, executorService);
    }

    @SneakyThrows
    private void discoverMessages() {
        while (igClient.isLoggedIn()) {
            if (isChannelEnabled) {
                executorService.submit(() -> {
                    igClient.sendRequest(new DirectInboxRequest().thread_message_limit(10))
                            .thenAccept(response -> {
                                Inbox receivedInbox = response.getInbox();
                                log.warn("Inbox received. Number of threads [{}]", receivedInbox.getThreads().size());
                                if (inboxHaveUnreadThreads(receivedInbox)) {
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
                                                        ChannelSessionDto channelSessionDto = new ChannelSessionDto();
                                                        channelSessionDto.setSessionId(key);
                                                        channelSessionDto.setChannelType(ChannelType.INSTAGRAM);
                                                        channelSessionDto.setChannelId(channelDto.getId());
                                                        channelSessionDto.setTenantId(channelDto.getTenantId());

                                                        ChannelMessageDto channelMessageDto = new ChannelMessageDto();
                                                        // FIXME: temp. On adding supporting of media we will need to process here data...
                                                        channelMessageDto.setText(((ThreadTextItem) item).getText());
                                                        channelMessageDto.setChannelSessionDto(channelSessionDto);
                                                        channelMessageDto.setInternalId(item.getItem_id());

                                                        localCache.add(item.getItem_id());

                                                        streamBridge.send(RECEIVED_MESSAGE_EXCHANGE, channelMessageDto);
                                                    }));
                                }
                            });
                });
            }
            // FIXME: first problem is a presence of hardcoded time to update.
            // FIXME: Second problem that it is an infinite loop. Use MQTT library to fix it
            Thread.sleep(5000);
        }
    }

    private boolean inboxHaveUnreadThreads(Inbox inbox) {
        return inbox.getUnseen_count() != 0; // this condition can produce a problem when somebody checking instagram messages from instagram application but not omnika
    }

    private boolean threadIsUnreadAndPrivate(IGThread igThread) {
        // TODO: investigate for is it needed to use only private threads
        return igThread.getRead_state() != 0 && "private".equals(igThread.getThread_type());
    }

    // FIXME: temporary. For now we receiving only text, but in future it should be media also (but for now only text)
    private boolean itemReceivedAndHaveTextType(ThreadItem threadItem) {
        return "text".equals(threadItem.getItem_type())
                && !((boolean) threadItem.getExtraProperties().get("is_sent_by_viewer"))
                && !localCache.contains(threadItem.getItem_id());
    }
}
