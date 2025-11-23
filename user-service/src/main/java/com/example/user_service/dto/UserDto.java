package com.example.user_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class UserDto {
    public UserDto(UUID id) {
        this.id = id;
    }

    private UUID id;
    private Long telegramUserId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
}
