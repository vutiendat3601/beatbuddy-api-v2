package vn.io.vutiendat3601.beatbuddy.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class RouteSpecificationCustomizer
    implements Customizer<
        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {
  @JsonProperty("route-specs")
  private List<RouteSpecification> routeSpecs = new LinkedList<>();

  private final String[] GET_AUTHENTICATED_ROUTES = {
    "/v1/auth/me", "/v1/me/playlists", "/v1/me/like", "/v1/auth/resources"
  };
  private final String[] POST_AUTHENTICATED_ROUTES = {
    "/v1/auth/resources",
  };
  private final String[] GET_PUBLIC_ROUTES = {
    "/v1/auth/token/client-token", "/apidocs*/**", "/swagger-ui/**"
  };

  @Override
  public void customize(
      AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry
          req) {
    for (RouteSpecification routeSpec : routeSpecs) {
      final String[] uris = routeSpec.getUris().toArray(String[]::new);
      final HttpMethod method = routeSpec.getMethod();
      final Set<String> authoritySet = routeSpec.getAuthorities();
      if (authoritySet.contains("permitAll")) {
        req.requestMatchers(method, uris).permitAll();
      } else if (authoritySet.contains("authenticated")) {
        req.requestMatchers(method, uris).authenticated();
      } else {
        final String[] authorities = authoritySet.toArray(String[]::new);
        req.requestMatchers(method, uris).hasAnyAuthority(authorities);
      }
    }

    // Deny all other requests
    req.anyRequest().denyAll();
  }
}
