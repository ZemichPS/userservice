package com.example.user_service.service.sm.registration.action;

import com.example.user_service.dto.UserDto;
import com.example.user_service.service.bot.TelegramTextSender;
import com.example.user_service.service.sm.AbstractAction;
import com.example.user_service.service.sm.registration.RegistrationEvent;
import com.example.user_service.service.sm.registration.RegistrationState;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component("nameInput")
public class NameInputAction extends AbstractAction<RegistrationState, RegistrationEvent> {

    protected NameInputAction(TelegramTextSender telegramTextSender) {
        super(telegramTextSender);
    }

    @Override
    public void execute(StateContext<RegistrationState, RegistrationEvent> context) {
        String chatId = context.getExtendedState().get("chatId", String.class);
        String name = context.getExtendedState().get("text", String.class);
        UserDto user = context.getExtendedState().get("user", UserDto.class);

        user.setFirstName(name);
        user.setLastName("telegramUser");

        SendMessage sm = createSendMessage(chatId);
        try {
            telegramTextSender.sendText(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        context.getExtendedState().getVariables().put("user", user);
    }

    @Override
    protected SendMessage createSendMessage(String chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Отлично! Голова, ты почти у цели! А теперь введи email")
                .build();
    }
}
