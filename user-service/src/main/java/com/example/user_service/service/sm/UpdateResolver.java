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
        String text = update.getMessage().getText();

        Scenario currentScenario;
        if (text.startsWith("/")) {
            String command = text.substring(1);
            currentScenario = Scenario.valueOf(command.toUpperCase());
            scenarioManager.setScenario(telegramUserId, currentScenario);
        }
        currentScenario = scenarioManager.getActiveScenario(telegramUserId);
        System.out.println("Текст: " + text + ". Текущий сценарий: " + currentScenario);
        scenarioHandlerHolder.handle(currentScenario, update);
    }
}
