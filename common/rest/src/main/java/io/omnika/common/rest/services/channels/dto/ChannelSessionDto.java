package io.omnika.common.rest.services.channels.dto;

import lombok.Data;

@Data
public class ChannelSessionDto {

    private Long sessionId;
    private SessionStatus sessionStatus;

    public enum SessionStatus {
        NEW, IN_PROCESS, CLOSED
    }

}
