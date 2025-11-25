package com.example.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder()
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    public UserDto(UUID id) {
        this.id = id;
    }
    private UUID id;
    private String telegramUserId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
}
