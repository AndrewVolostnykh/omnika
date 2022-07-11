package io.omnika.common.ipc.config;

import io.omnika.common.model.channel.ChannelType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Topics {

    public static String channelEvents(ChannelType channelType) {
        return "channel-events." + channelType.name().toLowerCase();
    }

    public static String getChannels() {
        return "get-channels";
    }

    public static String inboundChannelMessages() {
        return "inbound-channel-messages";
    }

    public static String outboundChannelMessages() {
        return "outbound-channel-messages";
    }

}
