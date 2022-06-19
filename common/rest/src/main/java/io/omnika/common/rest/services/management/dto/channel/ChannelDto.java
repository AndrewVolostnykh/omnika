package io.omnika.common.rest.services.management.dto.channel;

import com.fasterxml.jackson.databind.JsonNode;
import io.omnika.common.rest.services.management.model.ChannelType;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChannelDto {

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
