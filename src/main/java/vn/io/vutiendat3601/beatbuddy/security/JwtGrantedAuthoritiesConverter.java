package vn.io.vutiendat3601.beatbuddy.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessToken.Authorization;
import org.keycloak.representations.idm.authorization.Permission;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

@Slf4j
public class JwtGrantedAuthoritiesConverter
    implements Converter<Jwt, Collection<GrantedAuthority>> {
  private final ObjectMapper objMapper = new ObjectMapper();

  public JwtGrantedAuthoritiesConverter() {
    objMapper.findAndRegisterModules();
  }

  @Override
  public Collection<GrantedAuthority> convert(@NonNull Jwt jwt) {
    final Collection<GrantedAuthority> authorities = new LinkedList<>();
    Optional.ofNullable(jwt.getClaim("authorization"))
        .ifPresent(
            authzClaim -> {
              try {
                String serializedAuthzClaim = objMapper.writeValueAsString(authzClaim);
                Authorization authz =
                    objMapper.readValue(serializedAuthzClaim, Authorization.class);
                for (Permission permission : authz.getPermissions()) {
                  permission.getScopes().stream()
                      .map(SimpleGrantedAuthority::new)
                      .forEach(authorities::add);
                }
              } catch (JsonProcessingException e) {
                log.error("Jwt Authority convert error: {}", e);
              }
            });
    log.debug("Granted Authorities: {}", authorities);
    return authorities;
  }
}
