package com.popcorn.controller.user;

import com.google.gson.Gson;
import com.popcorn.api.UserAPI;
import com.popcorn.common.ChannelIdentifier;
import com.popcorn.common.Gender;
import com.popcorn.common.Region;
import com.popcorn.dto.request.CreateUserRequest;
import com.popcorn.dto.response.CreateUserResponse;
import com.popcorn.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * ✽ UserController is a REST API interface for user operations.
 * It defines an endpoint to create a new user.
 * The endpoint receives a request body with the user details and returns a response with the user details.
 * The controller logs the request details and the response details.
 * The controller implements the {@link UserAPI} interface to define the user operations.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserAPI {
    private final UserService userService;
    private final Gson jsonHelper;

    @Override
    public ResponseEntity<CreateUserResponse> createUser(String countryId, String question, String suggestion, Gender gender, Region region, ChannelIdentifier channelIdentifier, String channelType, String CHANNEL_TYPE, CreateUserRequest request, Principal principal) {
        log.info("UserController::createUser countryId: {}, question: {}, suggestion: {}, gender: {}, region: {}, channel-identifier: {}, CHANNEL_TYPE: {}, requestBody: {}", countryId, question, suggestion, gender, region, channelIdentifier, CHANNEL_TYPE, jsonHelper.toJson(request));
        if (principal instanceof UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
            log.info(jsonHelper.toJson(usernamePasswordAuthenticationToken));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }
}