package com.popcorn.cdc.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.popcorn.dto.request.CreateUserRequest;
import com.popcorn.dto.response.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(
        providerName = UserAPIConsumerContract.PROVIDER_NAME,
        hostInterface = "localhost",
        port = "18090"
)
@SpringBootTest
@ActiveProfiles(profiles = {"contract-test"})
@Slf4j
public class UserAPIConsumerContract {
    protected static final String PROVIDER_NAME = "user-api-provider";
    protected static final String CONSUMER_NAME = "user-api-consumer";

    @Pact(consumer = CONSUMER_NAME, provider = PROVIDER_NAME)
    public V4Pact createUser201Pact(PactBuilder builder) {
        String requestBody = """
                {
                    "name": "John Doe",
                    "email": "john.doe@email.com",
                    "password": "password"
                }
                """;
        String responseBody = """
                {
                    "userId": "dd65d4f2-3d5c-43a7-9c04-c4fa5d51dc2a",
                    "name": "John Doe",
                    "email": "john.doe@email.com"
                }
                """;
        return builder.usingLegacyDsl()
                .given("create user 201")
                .uponReceiving("request to create user with valid data")
                .path("/api/v1/users")
                //.headers(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .matchHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .method(HttpMethod.POST.name())
                //.matchHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody, MediaType.APPLICATION_JSON_VALUE)
                .willRespondWith()
                .status(201)
                .body(responseBody, MediaType.APPLICATION_JSON_VALUE)
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "createUser201Pact")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void pactTestForCreateUser201Pact(MockServer mockServer) {
        RestTemplate restTemplate = getRestTemplate();

        URI uri = UriComponentsBuilder.fromUriString(mockServer.getUrl()+"/api/v1/users").build().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        CreateUserRequest requestBody = CreateUserRequest.builder().name("John Doe").email("john.doe@email.com").password("password").build();

        restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(requestBody, headers), CreateUserResponse.class);
    }

    private RestTemplate getRestTemplate() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);
        return new RestTemplate(requestFactory);
    }
}
