package io.omnika.common.model.channel;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Channel {

    private UUID id;

    @NotNull
    private String name;

    // TODO: think about it. It is duplicate in config and here. Should it be?
    @NotNull
    private ChannelType channelType;

    private UUID tenantId;

    @Valid
    @NotNull
    private ChannelConfig config;

    private LocalDateTime createdTime;

    private LocalDateTime updateTime;

    private JsonNode metadata;

}
