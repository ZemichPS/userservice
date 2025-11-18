package com.example.user_service.service.statemachine;

import com.example.user_service.service.statemachine.registration.RegistrationEvent;
import com.example.user_service.service.statemachine.registration.RegistrationState;
import org.springframework.stereotype.Service;

@Service
public class RegistrationStateMachineService extends AbstractStateMachineService<RegistrationState, RegistrationEvent> {
}
