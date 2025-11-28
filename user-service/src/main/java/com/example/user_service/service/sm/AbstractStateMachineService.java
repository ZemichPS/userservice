package com.example.user_service.service.sm;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

public abstract class AbstractStateMachineService<S, E> {

    private final StateMachineFactory<S, E> stateMachineFactory;
    private final StateMachinePersister<S, E, String> persister;

    public AbstractStateMachineService(StateMachineFactory<S, E> stateMachineFactory,
                                       StateMachinePersist<S, E, String> persist) {
        this.stateMachineFactory = stateMachineFactory;
        persister = new DefaultStateMachinePersister<S, E, String>(persist);
    }

    public void handleEvent(String stateMachineId, Message<E> message) throws Exception {
        String telegramUserId = message.getHeaders().get("telegramUserId", String.class);
        StateMachine<S, E> stateMachine = stateMachineFactory.getStateMachine(telegramUserId);
        persister.restore(stateMachine, stateMachineId);

        if (stateMachine.getState() == null) {
            stateMachine.start();
        }

        if (!stateMachine.sendEvent(message)) throw new RuntimeException("Send event failed");
        persister.persist(stateMachine, stateMachineId);
    }

    public S getCurrentState(String stateMachineId) throws Exception {
        StateMachine<S, E> stateMachine = stateMachineFactory.getStateMachine();
        persister.restore(stateMachine, stateMachineId);
        if (stateMachine.getState() == null) {
            stateMachine.start();
        }
        return stateMachine.getState().getId();
    }

}
