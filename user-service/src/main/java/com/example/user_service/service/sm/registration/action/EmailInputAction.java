package com.example.user_service.service.sm.registration.action;

import com.example.user_service.dto.UserDto;
import com.example.user_service.service.UserService;
import com.example.user_service.service.bot.TelegramTextSender;
import com.example.user_service.service.sm.AbstractAction;
import com.example.user_service.service.sm.ScenarioManager;
import com.example.user_service.service.sm.registration.RegistrationEvent;
import com.example.user_service.service.sm.registration.RegistrationState;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;

@Component("emailInput")
public class EmailInputAction extends AbstractAction<RegistrationState, RegistrationEvent> {

    private final UserService userService;
    private final ScenarioManager scenarioManager;

    protected EmailInputAction(TelegramTextSender telegramTextSender,
                               UserService userService, ScenarioManager scenarioManager) {
        super(telegramTextSender);
        this.userService = userService;
        this.scenarioManager = scenarioManager;
    }

    @Override
    public void execute(StateContext<RegistrationState, RegistrationEvent> context) {
        String chatId = context.getExtendedState().get("chatId", String.class);
        String telegramUserId = context.getExtendedState().get("telegramUserId", String.class);
        String email = context.getExtendedState().get("text", String.class);
        UserDto user = context.getExtendedState().get("user", UserDto.class);

        user.setEmail(email);
        user.setBirthDate(LocalDate.now());
        user.setPassword("123456");

        SendMessage sm = createSendMessage(chatId);
        try {
            telegramTextSender.sendText(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        scenarioManager.clearScenario(Long.parseLong(telegramUserId));
        userService.create(user);
    }

    @Override
    protected SendMessage createSendMessage(String chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Супер! Мы тебя зарегистрировали, теперь ты наш пользователь.")
                .build();
    }

}
