package vn.io.vutiendat3601.beatbuddy.domain.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "Auth")
@RestController
@RequestMapping("v2/auth")
public class AuthController {
  private final AuthzClient webRepresentationClient;

  @GetMapping("client-token")
  public ResponseEntity<TokenDto> getClientToken() {
    final AccessTokenResponse accToken = webRepresentationClient.obtainAccessToken();
    return ResponseEntity.ok(
        new TokenDto(accToken.getToken(), accToken.getTokenType(), accToken.getExpiresIn()));
  }

  // @GetMapping("csrf-token")
  // public ResponseEntity<CsrfToken> getCsrfToken(final CsrfToken csrfToken) {
  //   return ResponseEntity.ok(csrfToken);
  // }
}
