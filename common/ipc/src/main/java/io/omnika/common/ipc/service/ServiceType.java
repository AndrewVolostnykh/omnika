package io.omnika.common.ipc.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum ServiceType {

    MANAGEMENT_SERVICE("management-service"),
    MESSAGING_GATEWAY("messaging-gateway-service"),

    TELEGRAM_CHANNEL("telegram-channel"),
    INSTAGRAM_CHANNEL("instagram-channel");

    private final String name;

    public static ServiceType getByName(String name) {
        return Arrays.stream(values())
                .filter(serviceType -> serviceType.getName().equals(name))
                .findFirst().orElse(null);
    }

}
