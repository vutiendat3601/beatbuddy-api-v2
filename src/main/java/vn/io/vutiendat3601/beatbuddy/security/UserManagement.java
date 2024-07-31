package vn.io.vutiendat3601.beatbuddy.security;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "security.client.user-management")
public class UserManagement {
  @JsonProperty("auth-server-url")
  private String authServerUrl;

  @JsonProperty("realm")
  private String realm;

  @JsonProperty("client-id")
  private String clientId;

  @JsonProperty("client-secret")
  private String clientSecret;

  @Bean
  Keycloak userManagementClient() {
    return KeycloakBuilder.builder()
        .serverUrl(authServerUrl)
        .realm(realm)
        .clientId(clientId)
        .clientSecret(clientSecret)
        .grantType(CLIENT_CREDENTIALS)
        .build();
  }
}
