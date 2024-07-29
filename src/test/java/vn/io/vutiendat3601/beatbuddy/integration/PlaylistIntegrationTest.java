package vn.io.vutiendat3601.beatbuddy.integration;

import static org.junit.Assert.assertFalse;

import com.github.javafaker.Faker;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.AbstractIntegrationTest;
import vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistCreateRequest;
import vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistDto;

public class PlaylistIntegrationTest extends AbstractIntegrationTest {
  private final Faker FAKER = new Faker();

  @Test
  public void canCreatePlaylist() {
    // Given
    final PlaylistCreateRequest playlistCreateReq =
        new PlaylistCreateRequest(
            FAKER.funnyName().name(), FAKER.lorem().paragraph(), faker.bool().bool(), Set.of());
    // When
    webTestClient
        .post()
        .uri("/v1/playlists")
        .body(Mono.just(playlistCreateReq), PlaylistCreateRequest.class)
        .exchange()
        .expectStatus()
        .isOk();
    final ResponseSpec actual = webTestClient.get().uri("/v1/playlists/me").exchange();

    // Then
    actual.expectStatus().isOk();
    actual
        .expectBodyList(PlaylistDto.class)
        .value(
            playlists -> {
              assertFalse(playlists.isEmpty());
            });
  }
}
