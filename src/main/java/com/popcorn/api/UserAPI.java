package com.popcorn.api;

import com.popcorn.dto.request.CreateUserRequest;
import com.popcorn.dto.response.CreateUserResponse;
import com.popcorn.dto.response.ExceptionDetailResponse;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.links.LinkParameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.popcorn.util.AppConstants.Headers.REQUEST_HEADER_CHANNEL_IDENTIFIER;
import static com.popcorn.util.AppConstants.Headers.REQUEST_HEADER_CHANNEL_TYPE;

public interface UserAPI {
    @Operation(
            summary = "Create a new user",
            tags = {"Users CRUD Operations Management"},
            description = "This endpoint allows the creation of a new user. The request should include the necessary details for creating a user.",
            method = "POST",
            operationId = "8beeb49f-9beb-4862-b36a-b889b45ed908",
            security = {@SecurityRequirement(name = "basicAuth"), @SecurityRequirement(name = "bearerAuth")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payload containing user details for creating a new user",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateUserRequest.class)
                    )
            ),
            parameters = {
                    @Parameter(
                            name = "countryId", required = true, in = ParameterIn.PATH,
                            schema = @Schema(implementation = String.class, title = "Country Identifier"),
                            description = "A unique identifier representing the country where the user is being created. This is typically a country code (e.g., 'US', 'IN')."
                    ),
                    @Parameter(
                            name = "question", required = true, in = ParameterIn.QUERY,
                            schema = @Schema(implementation = String.class, title = "Security Question"),
                            description = "A user-specific question, often used for security or customization purposes."
                    ),
                    @Parameter(
                            name = "suggestion", required = false, in = ParameterIn.QUERY,
                            schema = @Schema(implementation = String.class, title = "Suggestion"),
                            description = "Suggestion for the Security question (optional)"
                    ),
                    @Parameter(
                            name = REQUEST_HEADER_CHANNEL_IDENTIFIER, required = true, in = ParameterIn.HEADER,
                            schema = @Schema(implementation = String.class, title = "Channel Identifier", allowableValues = {"MOBILE", "WEB", "DESKTOP"} /*Add allowed values here*/),
                            description = "A mandatory header used to specify the originating channel of the request. Valid values include 'MOBILE' for mobile apps, 'WEB' for browser-based applications, and 'DESKTOP' for desktop applications."
                    ),
                    @Parameter(
                            name = REQUEST_HEADER_CHANNEL_TYPE, required = false, in = ParameterIn.HEADER,
                            schema = @Schema(implementation = String.class, title = "Channel Type"),
                            description = "An optional header used to specify the type of channel."
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "success", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreateUserResponse.class))}),
                    @ApiResponse(responseCode = "400", description = "bad request", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionDetailResponse.class))}),
                    @ApiResponse(responseCode = "401", description = "unauthorized", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionDetailResponse.class))}),
                    @ApiResponse(responseCode = "500", description = "internal server error", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionDetailResponse.class))}),
                    @ApiResponse(
                            responseCode = "503",
                            description = "service unavailable",
                            links = {
                                    @Link(
                                            name = "retryAfter",
                                            operationId = "retryServiceOperation", // Operation that handles retry
                                            description = "Link to retry the service after a certain period",
                                            parameters = {@LinkParameter(name = "retry-time")}
                                    ),
                                    @Link(
                                            name = "serviceStatus",
                                            operationRef = "/service-status",  // A reference to an existing operation in the OpenAPI definition
                                            description = "Link to check the service status",
                                            parameters = {@LinkParameter(name = "userId")}
                                    )},
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionDetailResponse.class),
                                    examples = {@ExampleObject(
                                            name = "exception detail response",
                                            value = """
                                                    {
                                                      "status": 503,
                                                      "message": "Internal server error",
                                                      "timestamp": "2024-11-15T19:33:52.955Z"
                                                    }
                                                    """,
                                            description = "Example of an error response when the service is unavailable. The 'status' field indicates the HTTP status code, 'message' provides a description of the error, and 'timestamp' records when the error occurred."
                                    )}
                            )})
            },
            externalDocs = @ExternalDocumentation(
                    description = "Find more details on the API usage",
                    url = "https://example.com/api-docs/user-management",
                    extensions = {
                            @Extension(name = "x-api-version", properties = {@ExtensionProperty(name = "version", value = "1.0", parseValue = true)}),
                            @Extension(name = "x-support", properties = {
                                    @ExtensionProperty(name = "contact", value = "support@example.com", parseValue = false),
                                    @ExtensionProperty(name = "phone", value = "+1-800-123-4567", parseValue = false)}
                            )
                    }
            )
    )
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping(value = "/{countryId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<CreateUserResponse> createUser(
            @PathVariable(value = "countryId") String countryId,
            @RequestParam(value = "question") String question,
            @RequestParam(value = "suggestion", required = false) String suggestion,
            @Pattern(regexp = "MOBILE|WEB|DESKTOP", message = "Invalid channel identifier") @RequestHeader(name = REQUEST_HEADER_CHANNEL_IDENTIFIER) final String CHANNEL_IDENTIFIER,
            @RequestHeader(name = REQUEST_HEADER_CHANNEL_TYPE, required = false) final String CHANNEL_TYPE,
            @RequestBody CreateUserRequest request,
            Principal principal
    );
}
