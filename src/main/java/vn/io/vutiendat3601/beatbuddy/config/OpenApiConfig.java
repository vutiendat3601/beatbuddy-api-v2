package vn.io.vutiendat3601.beatbuddy.config;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.OPENIDCONNECT;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@SecurityScheme(
    name = "web",
    openIdConnectUrl =
        "https://auth.beatbuddy.io.vn/realms/beatbuddy/.well-known/openid-configuration",
    scheme = "Bearer",
    bearerFormat = "JWT",
    type = OPENIDCONNECT,
    in = HEADER)
@OpenAPIDefinition(
    servers = {
      @Server(url = "https://api.beatbuddy.io.vn", description = "staging"),
      @Server(url = "http://localhost:8000", description = "development")
    },
    info = @Info(title = "BeatBuddy APIs", version = "1.0", description = "BeatBuddy APIs"))
public class OpenApiConfig {}
