package com.example.user_service.service.sm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
@Slf4j
public abstract class AbstractEventService<S, E> {

    private final AbstractStateMachineService<S, E> stateMachineService;

    protected AbstractEventService(AbstractStateMachineService<S, E> stateMachineService) {
        this.stateMachineService = stateMachineService;
    }

    public void processUserInput(Update update) throws Exception {
        Long telegramUserId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        S currentState = stateMachineService.getCurrentState(telegramUserId.toString());
        String text = update.getMessage().getText();
        log.info("current state: {}", currentState);
        E event = resolveEvent(currentState);
        log.info("Event: {} will be sent", event);
        stateMachineService.handleEvent(telegramUserId.toString(), MessageBuilder.withPayload(event)
                .setHeader("text", text)
                .setHeader("telegramUserId", telegramUserId.toString())
                .setHeader("chatId", chatId)
                .build());
    }

    abstract protected E resolveEvent(S currentState);
}
