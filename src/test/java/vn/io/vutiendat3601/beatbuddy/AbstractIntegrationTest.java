package vn.io.vutiendat3601.beatbuddy;

import com.github.javafaker.Faker;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AbstractIntegrationTest {
  @Autowired protected WebTestClient webTestClient;
  protected String token;
  protected final Faker faker = new Faker();
  protected final Keycloak keycloak =
      KeycloakBuilder.builder()
          .serverUrl("https://auth.beatbuddy.io.vn")
          .realm("beatbuddy")
          .clientId("test")
          .clientSecret("eAYp2yIMJ5usEpsc0avSPX3hIHtlGYw6")
          .username("vutien.dat.3601")
          .password("123456Aa@")
          .build();

  @BeforeEach
  void init() {
    webTestClient = webTestClient.mutate().responseTimeout(Duration.ofDays(1)).build();
    token = keycloak.tokenManager().getAccessTokenString();
  }

  @DynamicPropertySource
  static void registerDynamicProperties(
      org.springframework.test.context.DynamicPropertyRegistry registry) {
    registry.add("spring.docker.compose.skip.in-tests", () -> false);
  }
}
