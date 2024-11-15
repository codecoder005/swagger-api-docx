package com.popcorn.advice;

import com.popcorn.dto.response.ExceptionDetailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalAPIControllerAdvice {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ExceptionDetailResponse> handleException(Exception ex, WebRequest request) {
        log.error("GlobalAPIControllerAdvice::handleException {}", ex.getMessage());
        ex.printStackTrace();
        ExceptionDetailResponse response = ExceptionDetailResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ExceptionDetailResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error("GlobalAPIControllerAdvice::handleRuntimeException {}", ex.getMessage());
        ex.printStackTrace();
        ExceptionDetailResponse response = ExceptionDetailResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }
}