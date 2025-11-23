package com.example.user_service.service.sm;

import org.springframework.messaging.support.MessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractEventService<S, E> {

    private final AbstractStateMachineService<S, E> stateMachineService;

    protected AbstractEventService(AbstractStateMachineService<S, E> stateMachineService) {
        this.stateMachineService = stateMachineService;
    }

    public void processUserInput(Update update) throws Exception {
        Long telegramUserId = update.getMessage().getFrom().getId();
        S currentState = stateMachineService.getCurrentState(telegramUserId.toString());
        String text = update.getMessage().getText();
        E event = resolveEvent(currentState);
        stateMachineService.handleEvent(telegramUserId.toString(), MessageBuilder.withPayload(event)
                .setHeader("text", text)
                .setHeader("telegramUserId", telegramUserId.toString())
                .build());
    }

    abstract protected E resolveEvent(S currentState);
}
