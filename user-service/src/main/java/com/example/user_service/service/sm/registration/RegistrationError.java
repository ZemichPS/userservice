package com.example.user_service.service.sm.registration;

public enum RegistrationError {
    USER_ALREADY_REGISTERED("Вы уже зарегистрированы"),
    INVALID_EMAIL("Не валидный email");

    private final String errorMessage;

    RegistrationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
