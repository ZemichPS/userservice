package com.example.user_service.service.sm.registration;

import com.example.user_service.service.sm.AbstractAction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.*;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class MachineConfig extends EnumStateMachineConfigurerAdapter<RegistrationState, RegistrationEvent> {

        private final Map<String, AbstractAction<RegistrationState, RegistrationEvent>> actionRegistry;


        @Override
        public void configure(StateMachineConfigurationConfigurer<RegistrationState, RegistrationEvent> config) throws Exception {
            config.withConfiguration().autoStartup(false);

        }

        @Override
        public void configure(StateMachineStateConfigurer<RegistrationState, RegistrationEvent> states) throws Exception {
            states.withStates()
                    .initial(RegistrationState.START)
                    .end(RegistrationState.COMPLETED)
                    .states(EnumSet.allOf(RegistrationState.class));
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<RegistrationState, RegistrationEvent> transitions) throws Exception {
            transitions.withExternal()
                        .source(RegistrationState.START)
                        .target(RegistrationState.AWAITING_NAME)
                        .event(RegistrationEvent.START_REGISTRATION)
                        .action(actionRegistry.get("start"))
                    .and()
                    .withExternal()
                        .source(RegistrationState.AWAITING_NAME)
                        .target(RegistrationState.AWAITING_EMAIL)
                        .event(RegistrationEvent.SUBMIT_NAME)
                        .action(actionRegistry.get("nameInput"))
                    .and()
                    .withExternal()
                        .source(RegistrationState.AWAITING_EMAIL)
                        .target(RegistrationState.COMPLETED)
                        .event(RegistrationEvent.SUBMIT_EMAIL)
                        .guard(emailGuard())
                        .action(actionRegistry.get("emailInput"));
        }

        @Bean
        public Guard<RegistrationState, RegistrationEvent> emailGuard(){
            return context -> {
                String email = context.getMessage().getHeaders().get ("text", String.class);
                if(!email.contains("@"))   {
                    context.getExtendedState().getVariables().put("error", "Email address not valid");
                    return false;
                }

                if(email == null || email.isEmpty())   {
                    context.getExtendedState().getVariables().put("error", "Code error.");
                    return false;
                }

                return true;
            };
        }




}
