package com.popcorn.docx;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Swagger API Docx",
                version = "1.0.0",
                description = """
                Swagger API Docx Documentation
                """,
                termsOfService = "terms of service",
                contact = @Contact(
                        name = "Ramakrishna Janapureddy",
                        email = "developer.raakhi005@gmail.com"
                ),
                license = @License(
                        name = "Apache License 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        servers = {
                @Server(
                        description = "Local",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Development",
                        url = "http://13.201.228.185:8080"
                ),
                @Server(
                        description = "Test",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Production",
                        url = "http://15.206.151.118:8080"
                )
        }
)
@SecurityScheme(
        name = "apiKeyScheme",
        description = "API Key Authentication",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,  // Location of the API Key (HEADER or QUERY)
        scheme = "ApiKey"  // Optional field, typically used for distinguishing scheme type
)
@SecurityScheme(
        name = "basicAuth",
        description = "Basic Authentication Username and Password",
        scheme = "basic",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Bearer Authentication using JWT",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@SecurityScheme(
        name = "openidConnectScheme",
        description = "OpenID Connect Authentication",
        type = SecuritySchemeType.OPENIDCONNECT,
        openIdConnectUrl = "https://accounts.google.com/.well-known/openid-configuration"
)
@SecurityScheme(
        name = "mutualTlsScheme",
        description = "Mutual TLS Authentication (client and server both authenticate using certificates)",
        type = SecuritySchemeType.MUTUALTLS,
        in = SecuritySchemeIn.HEADER, // Assuming the certificate is provided via a custom header (optional, depends on implementation)
        scheme = "tls" // Specifies that the scheme is 'tls' for mutual TLS
)
@SecurityScheme(
        name = "oauth2Scheme",
        description = "OAuth2 Authentication using Access Token",
        type = SecuritySchemeType.OAUTH2,
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "https://example.com/oauth/authorize",
                        tokenUrl = "https://example.com/oauth/token",
                        scopes = {
                                @OAuthScope(name = "read", description = "Grants read access"),
                                @OAuthScope(name = "write", description = "Grants write access"),
                                @OAuthScope(name = "delete", description = "Grants delete access")
                        }
                )
        )
)
public class OpenAPIDocxConfig {

}