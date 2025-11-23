package com.example.user_service.service.sm;

import com.example.user_service.service.sm.registration.RegistrationEvent;
import com.example.user_service.service.sm.registration.RegistrationState;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class InMemoryStateMachinePersist implements StateMachinePersist<RegistrationState, RegistrationEvent, String> {

    private final HashMap<String, StateMachineContext<RegistrationState, RegistrationEvent>> contexts = new HashMap<>();

    @Override
    public void write(StateMachineContext<RegistrationState, RegistrationEvent> context, String contextObj) throws Exception {
        contexts.put(contextObj, context);
    }

    @Override
    public StateMachineContext<RegistrationState, RegistrationEvent> read(String contextObj) throws Exception {
        return contexts.get(contextObj);
    }
}
