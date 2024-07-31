package vn.io.vutiendat3601.beatbuddy.security;

import org.keycloak.authorization.client.AuthzClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {
  @Bean
  SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      AuthzClient resourceManagementClient,
      CorsConfigurationSource corsConfig,
      JwtAuthenticationConverter jwtAuthenticationConverter,
      Customizer<
              AuthorizeHttpRequestsConfigurer<HttpSecurity>
                  .AuthorizationManagerRequestMatcherRegistry>
          routeSpecCustomizer)
      throws Exception {
    return http.authorizeHttpRequests(routeSpecCustomizer)
        .oauth2ResourceServer(
            oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
        .cors(cors -> cors.configurationSource(corsConfig))
        .csrf(csrf -> csrf.disable())
        .addFilterBefore(
            new KeycloakExchangeAuthorizationTokenFilter(resourceManagementClient),
            BearerTokenAuthenticationFilter.class)
        .exceptionHandling(Customizer.withDefaults())
        .addFilterAfter(new DecodedJwtContextFilter(), AuthorizationFilter.class)
        .build();
  }
}
