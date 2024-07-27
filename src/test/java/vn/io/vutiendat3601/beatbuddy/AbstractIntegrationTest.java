package vn.io.vutiendat3601.beatbuddy;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AbstractIntegrationTest {
  @Autowired protected WebTestClient webTestClient;
  protected Faker faker = new Faker();

  @DynamicPropertySource
  static void registerDynamicProperties(
      org.springframework.test.context.DynamicPropertyRegistry registry) {
    registry.add("spring.docker.compose.skip.in-tests", () -> false);
  }
}
