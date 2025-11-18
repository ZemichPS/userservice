package com.example.user_service.service.statemachine;

public interface StateMachineService<E> {
    void handleEvent(String stateMachineId, E event) throws Exception;
}
