package com.popcorn.controller.ping;

import com.popcorn.api.PingAPI;
import com.popcorn.dto.response.PingAPIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class PingController implements PingAPI {
    @Override
    public ResponseEntity<PingAPIResponse> ping() {
        log.info("PingController::ping");
        var response = PingAPIResponse.builder().status(200).message("Up and Healthy").timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
