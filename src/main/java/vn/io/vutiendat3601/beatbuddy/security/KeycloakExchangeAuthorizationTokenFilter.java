package vn.io.vutiendat3601.beatbuddy.security;

import static vn.io.vutiendat3601.beatbuddy.security.SecurityConstant.CLIENT_TOKEN;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.ScopeRepresentation;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class KeycloakExchangeAuthorizationTokenFilter extends OncePerRequestFilter {
  private final AuthzClient authzClient;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest req,
      @NonNull HttpServletResponse resp,
      @NonNull FilterChain chain)
      throws ServletException, IOException {
    String token = req.getHeader(HttpHeaders.AUTHORIZATION);
    token = token == null ? req.getHeader(CLIENT_TOKEN) : token;
    if (token != null && token.startsWith("Bearer ") && token.length() > 7) {
      token = token.substring(7);

      // Find resources by matching uri
      final String uri = req.getServletPath();
      final List<ResourceRepresentation> resources =
          authzClient.protection().resource().findByMatchingUri(uri);
      if (!resources.isEmpty()) {
        final AuthorizationRequest authzRequest = new AuthorizationRequest();
        authzRequest.setSubjectToken(token);
        final Set<String> uris = new HashSet<>();
        final List<String> scopes = new LinkedList<>();
        for (ResourceRepresentation resource : resources) {
          uris.addAll(resource.getUris());
          scopes.addAll(resource.getScopes().stream().map(ScopeRepresentation::getName).toList());
          authzRequest.addPermission(resource.getId(), scopes);
        }
        log.debug("Found {} resources: uris={}", resources.size(), uris);
        log.debug("Attached with resources: scopes={}", scopes);

        // Get Authorization Token
        try {
          final AuthorizationResponse authzResponse =
              authzClient.authorization().authorize(authzRequest);

          // Inject new Jwt Authorization token to request
          final String jwtAuthorizationToken = authzResponse.getToken();
          req =
              new HttpServletRequestWrapper(req) {
                @Override
                public String getHeader(String name) {
                  if (HttpHeaders.AUTHORIZATION.equals(name)) {
                    return "Bearer " + jwtAuthorizationToken;
                  }
                  return super.getHeader(name);
                }
              };
        } catch (Exception e) {
          log.error("Get Authorization Token error: {}", e.getMessage());
        }
      }
    }
    chain.doFilter(req, resp);
  }
}
