package com.popcorn.controller;

import com.google.gson.Gson;
import com.popcorn.dto.request.CreateUserRequest;
import com.popcorn.filter.EveryHttpRequestInterceptorFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class UserControllerIT {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private EveryHttpRequestInterceptorFilter filter;
    @Autowired
    private Gson jsonHelper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(print())
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .addFilters(filter)
                .apply(springSecurity())
                .build();
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

        final String API_URL = "/api/v1/users/{countryId}";
        mockMvc.perform(
                    post(API_URL, "IND")
                            .header("channel-identifier", "WEB")
                            .header("channel-type", "BROWSER")
                            .param("question", "Who won 2024 US General elections")
                            .param("suggestion", "The man who challenges the world")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .characterEncoding(StandardCharsets.UTF_8.name())
                            .content(jsonHelper.toJson(requestBody))
                ).andExpect(status().isCreated())
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
                                .header("channel-identifier", "WEB")
                                .param("question", "Who won 2024 US General elections")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding(StandardCharsets.UTF_8.name())
                                .content(jsonHelper.toJson(requestBody))
                ).andExpect(status().isUnauthorized())
                .andReturn();
    }
}