package com.popcorn.controller;

import com.google.gson.Gson;
import com.popcorn.advice.GlobalAPIControllerAdvice;
import com.popcorn.common.CustomHeaders;
import com.popcorn.controller.user.UserController;
import com.popcorn.dto.request.CreateUserRequest;
import com.popcorn.dto.response.CreateUserResponse;
import com.popcorn.filter.EveryHttpRequestInterceptorFilter;
import com.popcorn.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@ActiveProfiles(profiles = {"unit-test"})
class UserControllerTest {
    @Autowired
    private EveryHttpRequestInterceptorFilter filter;
    @Autowired
    private UserController userController;
    @Autowired
    private GlobalAPIControllerAdvice controllerAdvice;
    @Autowired
    private Gson jsonHelper;

    @MockBean
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .addFilters(filter)
                .setControllerAdvice(controllerAdvice).build();
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = {"USER"})
    @DisplayName(value = "Create user 201")
    void testCreateUser() throws Exception {
        CreateUserRequest requestBody = CreateUserRequest.builder()
                .name("John Doe")
                .email("john.doe@gmail.com")
                .password("password")
                .build();
        CreateUserResponse responseBody = CreateUserResponse.builder()
                .userId(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@gmail.com")
                .build();

        when(userService.createUser(any()))
                .thenReturn(responseBody);

        final String API_URL = "/api/v1/users/{countryId}";
        mockMvc.perform(
                    post(API_URL, "IND")
                            .param("question", "Who won 2024 US General elections")
                            .param("suggestion", "The man who challenges the world")
                            .param("gender", "MALE")
                            .param("region", "AP_SOUTH_1")
                            .param("channel-identifier", "WEB")
                            .header(CustomHeaders.CHANNEL_TYPE, "Channel-Type")
                            .header(CustomHeaders.CLIENT_ID, "MockMvc.class")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .characterEncoding(StandardCharsets.UTF_8.name())
                            .content(jsonHelper.toJson(requestBody))
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@gmail.com"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = {"EMPLOYEE"})
    @DisplayName(value = "Authorization Denied Exception")
    void testCreateUserShouldThrowAuthorizationDeniedException() throws Exception {
        CreateUserRequest requestBody = CreateUserRequest.builder()
                .name("John Doe")
                .email("john.doe@gmail.com")
                .password("password")
                .build();

        final String API_URL = "/api/v1/users/{countryId}";
        mockMvc.perform(
                        post(API_URL, "IND")
                                .param("question", "Who won 2024 US General elections")
                                .param("suggestion", "The man who challenges the world")
                                .param("gender", "MALE")
                                .param("region", "AP_SOUTH_1")
                                .param("channel-identifier", "WEB")
                                .header(CustomHeaders.CHANNEL_TYPE, "Channel-Type")
                                .header(CustomHeaders.CLIENT_ID, "MockMvc.class")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding(StandardCharsets.UTF_8.name())
                                .content(jsonHelper.toJson(requestBody))
                ).andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn();
    }
}