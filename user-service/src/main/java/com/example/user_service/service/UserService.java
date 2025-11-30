package com.example.user_service.service;

import com.example.user_service.dto.UserDto;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto getById(UUID id) {
        return userRepository.findById(id)
                .map(UserMapper::toUserDto)
                .orElseThrow();
    }

    @Transactional
    public UserDto create(UserDto userDto) {
        User newUser = UserMapper.toUser(userDto);
        User savedUser = userRepository.save(newUser);
        return UserMapper.toUserDto(savedUser);
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserDto)
                .toList();
    }
}
