package io.omnika.services.telegram.channel.web.controller;

import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelConfig;
import io.omnika.services.telegram.channel.core.service.BotChannelProvider;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// only for testing controller
@RestController
@RequiredArgsConstructor
public class CreateBotController {

    private final BotChannelProvider botChannelProvider;
    private final DiscoveryClient discoveryClient;

    @PostMapping
    public void createBot(@RequestBody TelegramBotChannelConfig telegramBotChannelDto) {

//        botChannelProvider.createBot(telegramBotChannelDto);

    }

//    @GetMapping
//    public List<TelegramBotChannelConfig> list() {
////        return botChannelProvider.list();
//    }

    @GetMapping("/discovery/info")
    public DiscoveryInfo getDiscoveryInfo() {
        DiscoveryInfo discoveryInfo = new DiscoveryInfo();
        discoveryInfo.desc = discoveryClient.description();
        discoveryInfo.services = discoveryClient.getServices();
        discoveryInfo.instances = discoveryClient.getInstances("telegram-channel").stream().map(ServiceInstance::getServiceId).collect(
                Collectors.toList());

        return discoveryInfo;
    }

    static class DiscoveryInfo {
        public String desc;
        public List<String> instances;
        public List<String> services;
    }
}
