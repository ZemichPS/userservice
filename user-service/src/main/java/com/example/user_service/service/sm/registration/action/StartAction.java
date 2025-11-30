package com.example.user_service.service.sm.registration.action;

import com.example.user_service.dto.UserDto;
import com.example.user_service.service.UserService;
import com.example.user_service.service.bot.TelegramTextSender;
import com.example.user_service.service.sm.AbstractAction;
import com.example.user_service.service.sm.registration.RegistrationEvent;
import com.example.user_service.service.sm.registration.RegistrationState;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.UUID;

@Component("start")
public class StartAction extends AbstractAction<RegistrationState, RegistrationEvent> {


    protected StartAction(TelegramTextSender telegramTextSender) {
        super(telegramTextSender);
    }

    @Override
    public void execute(StateContext<RegistrationState, RegistrationEvent> context) {
        String chatId = context.getMessage().getHeaders().get("chatId", String.class);

        SendMessage sm = createSendMessage(chatId);
        try {
            telegramTextSender.sendText(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected SendMessage createSendMessage(String chatId) {
        return SendMessage.builder()
                .text("Хелова! Рад что ты решил зарегистрироваться! Введи своё имя, голова")
                .chatId(chatId)
                .build();
    }
}
