package vn.io.vutiendat3601.beatbuddy.security;

import org.keycloak.authorization.client.AuthzClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSocketSecurity
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
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf((csrf) -> csrf.disable()) // CSRF is not neccessary when no Session used
        .oauth2ResourceServer(
            oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
        .cors(cors -> cors.configurationSource(corsConfig))
        .addFilterBefore(
            new KeycloakExchangeAuthorizationTokenFilter(resourceManagementClient),
            BearerTokenAuthenticationFilter.class)
        .build();
  }

  @Bean
  AuthorizationManager<Message<?>> messageAuthorizationManager(
      MessageMatcherDelegatingAuthorizationManager.Builder websocket) {
    return websocket.anyMessage().permitAll().build();
  }

  @Bean // Override csrfChannelInterceptor Bean created @EnableWebSocketSecurity
  ChannelInterceptor csrfChannelInterceptor() { // CSRF is not neccessary when no Session used
    return new ChannelInterceptor() {}; // Do nothing interceptor which disable the CSRF
  }
}
