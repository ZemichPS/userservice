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
import java.util.UUID;

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
        String telegramUserId = context.getMessage().getHeaders().get("telegramUserId", String.class);
        String chatId = context.getMessage().getHeaders().get("chatId", String.class);
        String email = context.getMessage().getHeaders().get("text", String.class);
        String username = context.getExtendedState().get("username", String.class);

        UserDto user = new UserDto(UUID.randomUUID());
        user.setLastName("telegramUser");
        user.setFirstName(username);
        user.setEmail(email);
        user.setBirthDate(LocalDate.now());
        user.setPassword("123456");
        user.setTelegramUserId(telegramUserId);
        SendMessage sm = createSendMessage(chatId);
        userService.create(user);

        try {
            telegramTextSender.sendText(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        scenarioManager.clearScenario(Long.parseLong(telegramUserId));
    }

    @Override
    protected SendMessage createSendMessage(String chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Супер! Мы тебя зарегистрировали, теперь ты наш пользователь.")
                .build();
    }

}
