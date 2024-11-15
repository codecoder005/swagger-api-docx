package com.popcorn.controller;

import com.popcorn.api.UserAPI;
import com.popcorn.dto.request.CreateUserRequest;
import com.popcorn.dto.response.CreateUserResponse;
import com.popcorn.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserAPI {
    private final UserService userService;

    @Override
    public ResponseEntity<CreateUserResponse> createUser(final String CHANNEL_IDENTIFIER, CreateUserRequest request) {
        log.info("UserController::createUser {}, {}", CHANNEL_IDENTIFIER, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }
}