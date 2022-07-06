package io.omnika.common.model.channel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramBotChannelConfig implements ChannelConfig {

    @NotNull
    private String botName;

    @NotNull
    private String apiKey;

    @Override
    public ChannelType getType() {
        return ChannelType.TELEGRAM_BOT;
    }
}
