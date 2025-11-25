package com.example.user_service.service.sm.scenariohandler;

import com.example.user_service.service.sm.Scenario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@RequiredArgsConstructor
public class UndefinedScenarioHandler implements ScenarioHandler {

    private final TelegramClient telegramClient;

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = SendMessage.builder()
                .text("Команда не распознана")
                .chatId(String.valueOf(chatId))
                .build();
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Scenario getScenarioType() {
        return Scenario.UNDEFINED;
    }
}
