package io.omnika.common.rest.services.management.dto.channel;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.omnika.common.rest.services.management.dto.TenantDto;
import io.omnika.common.rest.services.management.model.ChannelType;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "channelType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TelegramBotChannelDto.class, name = "TELEGRAM_BOT"),
        @JsonSubTypes.Type(value = ViberBotChannelDto.class, name = "VIBER_BOT")
})
public class ChannelDto {

    protected Long id;

    @NotNull
    protected String name;

    protected ChannelType channelType;

    protected TenantDto tenantDto;

}
