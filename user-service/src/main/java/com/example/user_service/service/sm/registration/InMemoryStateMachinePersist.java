package com.example.user_service.service.sm.registration;

import lombok.extern.log4j.Log4j2;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Log4j2
public class InMemoryStateMachinePersist implements StateMachinePersist<RegistrationState, RegistrationEvent, String> {

    private final Map<String, StateMachineContext<RegistrationState, RegistrationEvent>> contexts =
            new ConcurrentHashMap<>();

    @Override
    public void write(StateMachineContext<RegistrationState, RegistrationEvent> context, String contextObj) throws Exception {
        contexts.put(contextObj, context);
        log.info("\uD83D\uDCBE Saved context for: {}, state: {}", contextObj, context.getState());
    }

    @Override
    public StateMachineContext<RegistrationState, RegistrationEvent> read(String contextObj) throws Exception {
        StateMachineContext<RegistrationState, RegistrationEvent> context = contexts.get(contextObj);


        if (context != null) {
            log.info("Contex restored. State: {}", context.getState());
        } else {
            log.error("contex was not restored. Context is null");
        }
        // Если контекста нет - возвращаем null, StateMachine создаст новый с initial state
        return context;
    }
}