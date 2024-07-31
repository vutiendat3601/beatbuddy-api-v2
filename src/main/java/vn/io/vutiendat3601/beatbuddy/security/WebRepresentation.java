package vn.io.vutiendat3601.beatbuddy.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;
import org.keycloak.authorization.client.AuthzClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "security.client.web-representation")
public class WebRepresentation {
  @JsonProperty("auth-server-url")
  private String authServerUrl;

  @JsonProperty("realm")
  private String realm;

  @JsonProperty("client-id")
  private String clientId;

  @JsonProperty("client-secret")
  private String clientSecret;

  @Bean
  AuthzClient webRepresentationClient() {
    final org.keycloak.authorization.client.Configuration config =
        new org.keycloak.authorization.client.Configuration(
            authServerUrl, realm, clientId, Map.of("secret", clientSecret), null);
    return AuthzClient.create(config);
  }
}
