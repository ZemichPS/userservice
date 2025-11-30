package com.example.user_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "app", name = "users")
public class User {
    @Id
    private UUID id;
    private String telegramUserId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
}
