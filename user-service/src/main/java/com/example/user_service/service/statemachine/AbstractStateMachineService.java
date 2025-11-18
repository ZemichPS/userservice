package com.example.user_service.service.statemachine;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;

public abstract class AbstractStateMachineService<S, E> implements StateMachineService<E> {

    private StateMachineFactory<S, E> stateMachineFactory;
    private StateMachinePersister<S, E, String> persister;

    public void handleEvent(String stateMachineId, E event) throws Exception {
        StateMachine<S,E> stateMachine = stateMachineFactory.getStateMachine();
        persister.restore(stateMachine, stateMachineId);
        stateMachine.start();
        stateMachine.sendEvent(event);
        persister.persist(stateMachine,stateMachineId);
    }

}
