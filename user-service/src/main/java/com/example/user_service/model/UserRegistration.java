package com.example.user_service.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistration {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
}
