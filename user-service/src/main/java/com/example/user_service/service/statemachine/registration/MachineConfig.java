package com.example.user_service.service.statemachine.registration;

import com.example.user_service.dto.UserDto;
import com.example.user_service.model.User;
import com.example.user_service.service.UserService;
import com.example.user_service.service.statemachine.StateMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;
import java.util.UUID;

@Configuration
@EnableStateMachine
@RequiredArgsConstructor
public class MachineConfig extends EnumStateMachineConfigurerAdapter<RegistrationState, RegistrationEvent> {



    private final Action<RegistrationState, RegistrationEvent> nameInsertAction = context -> System.out.println("name insert action");
    private final Action<RegistrationState, RegistrationEvent> emailInsertAction = context -> System.out.println("email insert action");
    private final Action<RegistrationState, RegistrationEvent> completeAction = context -> System.out.println("registration completed");

    @Override
    public void configure(StateMachineStateConfigurer<RegistrationState, RegistrationEvent> states) throws Exception {
        states.withStates()
                .initial(RegistrationState.START)
                .end(RegistrationState.COMPLETE)
                .states(EnumSet.allOf(RegistrationState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<RegistrationState, RegistrationEvent> transitions) throws Exception {
        transitions.withExternal()
                .source(RegistrationState.START)
                .target(RegistrationState.NAME_INSERT)
                .event(RegistrationEvent.STARTED)
                .action(initAction())
                .and()
                .withExternal()
                .source(RegistrationState.NAME_INSERT)
                .target(RegistrationState.EMAIL_INSERT)
                .event(RegistrationEvent.NAME_INSERTED)
                .action(nameInsertAction)
                .and()
                .withExternal()
                .source(RegistrationState.EMAIL_INSERT)
                .target(RegistrationState.COMPLETE)
                .event(RegistrationEvent.COMPLETED)
                .action(completeAction);
    }

    @Bean
    public Action<RegistrationState, RegistrationEvent> initAction() {
        return context -> {
            User newUser = new User();
            newUser.setId(UUID.randomUUID());

            try {
                stateMachineService.handleEvent(newUser.getId().toString(), RegistrationEvent.STARTED);
                userService.save(newUser);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
