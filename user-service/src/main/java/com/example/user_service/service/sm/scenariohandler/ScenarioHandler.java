package com.example.user_service.service.sm.scenariohandler;

import com.example.user_service.service.sm.Scenario;
import com.example.user_service.service.sm.ScenarioHandlerHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ScenarioHandler {
    void handle(Update update);

    Scenario getScenarioType();

    @Autowired
    default void regMe(ScenarioHandlerHolder commandHandler){
        commandHandler.register(getScenarioType(), this);
    }


}
