package vn.io.vutiendat3601.beatbuddy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
public class JwtAuthenticationConverterConfig {
  @Bean
  JwtAuthenticationConverter jwtAuthenticationConverter() {
    final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
        new JwtGrantedAuthoritiesConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }
}
