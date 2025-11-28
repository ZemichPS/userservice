package com.example.user_service.service.sm.registration;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryStateMachinePersist implements StateMachinePersist<RegistrationState, RegistrationEvent, String> {

    private final Map<String, StateMachineContext<RegistrationState, RegistrationEvent>> contexts =
            new ConcurrentHashMap<>();

    @Override
    public void write(StateMachineContext<RegistrationState, RegistrationEvent> context, String contextObj) throws Exception {
        contexts.put(contextObj, context);
        System.out.println("💾 Saved context for: " + contextObj + ", state: " + context.getState());
    }

    @Override
    public StateMachineContext<RegistrationState, RegistrationEvent> read(String contextObj) throws Exception {
        StateMachineContext<RegistrationState, RegistrationEvent> context = contexts.get(contextObj);
        System.out.println("📖 Restored context for: " + contextObj +
                ", state: " + (context != null ? context.getState() : "null (new machine)"));

        // Если контекста нет - возвращаем null, StateMachine создаст новый с initial state
        return context;
    }
}