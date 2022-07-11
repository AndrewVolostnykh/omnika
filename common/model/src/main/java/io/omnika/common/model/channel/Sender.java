package io.omnika.common.model.channel;

import lombok.Data;

import java.util.UUID;

@Data
public class Sender {

    private UUID id;
    private String name;
    private String nickName;
    private String avatarUrl;

    private String externalId;
    private ChannelType channelType;

}
