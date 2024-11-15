package com.popcorn.service;

import com.google.gson.Gson;
import com.popcorn.dto.request.CreateUserRequest;
import com.popcorn.dto.response.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final Gson jsonHelper;

    public CreateUserResponse createUser(CreateUserRequest request) {
        log.info("UserService::createUser {}", jsonHelper.toJson(request));
        return CreateUserResponse.builder()
                .userId(UUID.randomUUID())
                .name(request.getName())
                .email(request.getEmail())
                .build();
    }
}
