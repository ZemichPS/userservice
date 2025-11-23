package com.example.user_service.configuration;

import com.example.user_service.service.bot.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
@RequiredArgsConstructor
public class BotConfig {

    private final String token = "8335225261:AAHYWDZaV3d2CzoIzu4vKwb1Ulo9g3PBN1I";

    @Bean
    public TelegramBotsLongPollingApplication botsApplication(TelegramBot bot) throws TelegramApiException {
        TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
        botsApplication.registerBot(token, bot);
        return botsApplication;
    }

    @Bean
    public TelegramClient telegramClient(){
        return new OkHttpTelegramClient(token);
    }
}
