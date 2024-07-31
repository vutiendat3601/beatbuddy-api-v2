package vn.io.vutiendat3601.beatbuddy.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class CorsConfig implements CorsConfigurationSource {
  @NonNull
  @Override
  public CorsConfiguration getCorsConfiguration(@NonNull HttpServletRequest req) {
    final CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.addAllowedOrigin("*");
    corsConfig.addAllowedMethod("*");
    corsConfig.addAllowedHeader("*");
    return corsConfig;
  }
}
