package io.omnika.common.ipc.config;

import io.omnika.common.model.channel.ChannelType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Topics {

    public static String newChannels(ChannelType channelType) {
        return "new-channels." + channelType.name().toLowerCase();
    }

    public static String getChannels() {
        return "get-channels";
    }

    public static String newChannelMessages() {
        return "new-channel-messages";
    }

}
