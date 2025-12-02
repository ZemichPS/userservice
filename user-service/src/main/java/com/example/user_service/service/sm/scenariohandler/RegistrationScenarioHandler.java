package com.example.user_service.service.sm.scenariohandler;

import com.example.user_service.service.sm.Scenario;
import com.example.user_service.service.sm.registration.RegistrationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class RegistrationScenarioHandler implements ScenarioHandler {

    private final RegistrationEventService eventService;

    @Override
    public void handle(Update update) throws Exception {
            eventService.processUserInput(update);
    }

    @Override
    public Scenario getScenarioType() {
        return Scenario.REGISTRATION;
    }
}
