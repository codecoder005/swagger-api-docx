package com.popcorn.client.feign;


import com.popcorn.common.ChannelIdentifier;
import com.popcorn.common.CustomHeaders;
import com.popcorn.common.Gender;
import com.popcorn.common.Region;
import com.popcorn.dto.request.CreateUserRequest;
import com.popcorn.dto.response.CreateUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "remoteUserAPIClient", url = "${outbound.remote-user-api-client.url}")
public interface RemoteUserAPIClient {
    @PostMapping(value = "/{countryId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<CreateUserResponse> createUser(
            @PathVariable(value = "countryId") String countryId,
            @RequestParam(value = "question") String question,
            @RequestParam(value = "suggestion", required = false) String suggestion,
            @RequestParam(value = "gender") Gender gender,
            @RequestParam(value = "region") Region region,
            @RequestParam(value = "channel-identifier") ChannelIdentifier channelIdentifier,
            @RequestHeader(name = CustomHeaders.CHANNEL_TYPE, required = false) final String channelType,
            @RequestHeader(name = CustomHeaders.CLIENT_ID) final String clientId,
            @RequestBody CreateUserRequest request
    );
}
