package com.example.user_service.service.sm.registration;

import com.example.user_service.service.sm.AbstractStateMachineService;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

@Service
public class RegistrationStateMachineService extends AbstractStateMachineService<RegistrationState, RegistrationEvent> {

    public RegistrationStateMachineService(StateMachineFactory<RegistrationState, RegistrationEvent> stateMachineFactory, StateMachinePersist<RegistrationState, RegistrationEvent, String> persist) {
        super(stateMachineFactory, persist);
    }
}
