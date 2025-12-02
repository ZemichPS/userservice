package com.example.user_service.service.sm;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractStateMachineService<S, E> {

    private final StateMachineFactory<S, E> stateMachineFactory;
    private final StateMachinePersister<S, E, String> persister;

    public AbstractStateMachineService(StateMachineFactory<S, E> stateMachineFactory,
                                       StateMachinePersist<S, E, String> persist) {
        this.stateMachineFactory = stateMachineFactory;
        persister = new DefaultStateMachinePersister<S, E, String>(persist);
    }

    public SendResult handleEvent(String stateMachineId, Message<E> message) throws Exception {
        String telegramUserId = message.getHeaders().get("telegramUserId", String.class);
        StateMachine<S, E> stateMachine = stateMachineFactory.getStateMachine(telegramUserId);
        persister.restore(stateMachine, stateMachineId);
        clearPreviousErrors(stateMachine);

        if (stateMachine.getState() == null) {
            stateMachine.start();
        }

        boolean eventSendingResult = stateMachine.sendEvent(message);
        SendResult sentResult = createSendResult(eventSendingResult, stateMachine);

        persister.persist(stateMachine, stateMachineId);
        return sentResult;
    }

    private SendResult createSendResult(boolean eventSendingResult, StateMachine<S, E> stateMachine) {
        List<String> errors = stateMachine.getExtendedState().get("errors", List.class);
    }

    public S getCurrentState(String stateMachineId) throws Exception {
        StateMachine<S, E> stateMachine = stateMachineFactory.getStateMachine();
        persister.restore(stateMachine, stateMachineId);
        if (stateMachine.getState() == null) {
            stateMachine.start();
        }
        return stateMachine.getState().getId();
    }

    private void clearPreviousErrors(StateMachine<S, E> stateMachine){
        stateMachine.getExtendedState().getVariables().getOrDefault("errors", new HashMap<>());
    }

}
