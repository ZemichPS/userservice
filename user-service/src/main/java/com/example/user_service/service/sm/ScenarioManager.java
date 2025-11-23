package com.example.user_service.service.sm;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ScenarioManager {
    private final Map<Long, Scenario> activeScenarios = new ConcurrentHashMap<>();

    public Scenario getActiveScenario(Long userId) {
        return activeScenarios.getOrDefault(userId, Scenario.UNDEFINED);
    }

    public void setScenario(Long userId, Scenario scenario) {
        activeScenarios.put(userId, scenario);
    }

    public void clearScenario(Long userId) {
        activeScenarios.put(userId, Scenario.UNDEFINED);
    }
}
