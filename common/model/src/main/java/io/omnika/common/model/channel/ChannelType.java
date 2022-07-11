package io.omnika.common.model.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ChannelType {
    TELEGRAM_BOT(ServiceType.TELEGRAM_CHANNEL),
    VIBER_BOT(null),
    INSTAGRAM(ServiceType.INSTAGRAM_CHANNEL);

    private final ServiceType channelServiceType;
}
