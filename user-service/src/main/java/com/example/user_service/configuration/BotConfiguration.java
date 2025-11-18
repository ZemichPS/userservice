package com.example.user_service.configuration;

import io.github.natanimn.telebof.BotClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfiguration {

    private final String token = "8335225261:AAHYWDZaV3d2CzoIzu4vKwb1Ulo9g3PBN1I";

    @Bean
    BotClient botClient (){
        BotClient bot = new BotClient(token);
        bot.startPolling();
        return bot;
    }

}
