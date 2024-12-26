package com.popcorn.dto.response;

import lombok.*;

import java.time.LocalDateTime;

/**
 * ✽ PingAPIResponse is a DTO class that represents the response of the PingAPI.
 * It contains the status code, a message indicating the status, and the timestamp of the response.
 * This class is used to serialize the response of the PingAPI into JSON format.
 * The JSON format of the response will look like this:
 * {
 *    "status": 200,
 *    "message": "Up and Healthy",
 *    "timestamp": "2021-09-01T12:00:00"
 * }
 * The response will be sent back to the client as an HTTP response.
 * The client can use this information to verify that the service is up and running.
 * 
 * @see com.popcorn.api.PingAPI
 * @see com.popcorn.controller.ping.PingController
 * 
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PingAPIResponse {
    private Integer status;
    private String message;
    private LocalDateTime timestamp;
}
