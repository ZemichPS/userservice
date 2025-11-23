package com.example.user_service.service.sm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class UpdateResolver {

    private final ScenarioHandlerHolder scenarioHandlerHolder;
    private final ScenarioManager scenarioManager;

    public void resolve(Update update) {
        Long telegramUserId = update.getMessage().getFrom().getId();
        Scenario currentScenario = scenarioManager.getActiveScenario(telegramUserId);
        scenarioHandlerHolder.handle(currentScenario, update);
    }
}
