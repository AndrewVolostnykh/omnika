package io.omnika.common.rest.services.management.dto.channel;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.omnika.common.rest.services.management.model.ChannelType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TelegramBotChannelConfig.class, name = "TELEGRAM_BOT"),
        @JsonSubTypes.Type(value = ViberBotChannelConfig.class, name = "VIBER_BOT"),
        @JsonSubTypes.Type(value = InstagramChannelConfig.class, name = "INSTAGRAM")
})
public interface ChannelConfig {

    ChannelType getType();

}
