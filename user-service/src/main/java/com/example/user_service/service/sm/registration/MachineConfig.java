package com.example.user_service.service.sm.registration;

import com.example.user_service.service.sm.AbstractAction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;
import java.util.Map;

@Configuration
@EnableStateMachine
@RequiredArgsConstructor
public class MachineConfig extends EnumStateMachineConfigurerAdapter<RegistrationState, RegistrationEvent> {

    private final Map<String, AbstractAction<RegistrationState, RegistrationEvent>> actionMap;

    @Override
    public void configure(StateMachineStateConfigurer<RegistrationState, RegistrationEvent> states) throws Exception {
        states.withStates()
                .initial(RegistrationState.START)
                .end(RegistrationState.AWAITING_EMAIL)
                .states(EnumSet.allOf(RegistrationState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<RegistrationState, RegistrationEvent> transitions) throws Exception {
        transitions.withExternal()
                    .source(RegistrationState.START)
                    .target(RegistrationState.AWAITING_NAME)
                    .event(RegistrationEvent.START_REGISTRATION)
                    .action(actionMap.get("start"))
                .and()
                .withExternal()
                    .source(RegistrationState.AWAITING_NAME)
                    .target(RegistrationState.AWAITING_EMAIL)
                    .event(RegistrationEvent.SUBMIT_NAME)
                    .action(actionMap.get("nameInput"))
                .and()
                .withExternal()
                    .source(RegistrationState.AWAITING_EMAIL)
                    .target(RegistrationState.COMPLETED)
                    .event(RegistrationEvent.SUBMIT_EMAIL)
                    .action(actionMap.get("emailInput"));

    }


}
