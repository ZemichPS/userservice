package com.example.user_service.presentation;

import com.example.user_service.dto.UserDto;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/id")
    public ResponseEntity<UserDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping
    public ResponseEntity<URI> create(@RequestBody UserDto userDto) {
        if (userDto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        UserDto createdUser = userService.create(userDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


}
