package com.example.user_service.service.sm.registration;

import com.example.user_service.service.sm.AbstractEventService;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RegistrationEventService extends AbstractEventService<RegistrationState, RegistrationEvent> {

    protected RegistrationEventService(RegistrationStateMachineService stateMachineService) {
        super(stateMachineService);
    }

    @Override
    protected RegistrationEvent resolveEvent(@NotNull RegistrationState currentState) {
        return switch (currentState) {
            case START -> RegistrationEvent.START_REGISTRATION;
            case AWAITING_NAME -> RegistrationEvent.SUBMIT_NAME;
            case AWAITING_EMAIL -> RegistrationEvent.SUBMIT_EMAIL;
            default -> RegistrationEvent.UNKNOWN;
        };
    }
}
