package com.example.user_service.service.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@RequiredArgsConstructor
public class TelegramTextSender implements TextSender<SendMessage> {

    private final TelegramClient telegramClient;

    @Override
    public void sendText(SendMessage message) throws TelegramApiException {
        telegramClient.execute(message);
    }
}
