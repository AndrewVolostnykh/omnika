package io.omnika.services.channel.telegram;

import io.omnika.common.channel.api.config.EnableChannelApi;
import io.omnika.common.ipc.config.EnableIpc;
import io.omnika.common.security.config.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@EnableIpc
@EnableChannelApi
@SpringBootApplication
public class TelegramChannelApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramChannelApplication.class, args);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class);
    }

}
