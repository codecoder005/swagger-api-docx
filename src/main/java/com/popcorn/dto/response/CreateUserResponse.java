package com.popcorn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Getter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserResponse {
    private UUID userId;
    private String name;
    private String email;
}
