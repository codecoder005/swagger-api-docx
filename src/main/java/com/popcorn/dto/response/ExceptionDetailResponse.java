package com.popcorn.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionDetailResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
