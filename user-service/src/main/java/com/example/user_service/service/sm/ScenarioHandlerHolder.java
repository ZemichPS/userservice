package com.example.user_service.service.sm;

import com.example.user_service.service.sm.scenariohandler.ScenarioHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
public class ScenarioHandlerHolder {

    private final Map<Scenario, ScenarioHandler> handlerMap = new HashMap<>();

    public void register(Scenario scenario, ScenarioHandler handler) {
        handlerMap.put(scenario, handler);
    }

    public void handle(Scenario scenario, Update update) {
        ScenarioHandler handler = handlerMap.getOrDefault(scenario, handlerMap.get(Scenario.UNDEFINED));
        handler.handle(update);
    }
}
