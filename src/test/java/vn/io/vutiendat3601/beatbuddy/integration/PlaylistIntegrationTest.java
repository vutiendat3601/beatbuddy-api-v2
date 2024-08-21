package vn.io.vutiendat3601.beatbuddy.integration;

import static org.junit.Assert.assertFalse;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.AbstractIntegrationTest;
import vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistCreateRequest;
import vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistDto;

public class PlaylistIntegrationTest extends AbstractIntegrationTest {
  @BeforeEach
  void setUp() {}

  // @Test
  public void canCreatePlaylist() {
    // Given
    final PlaylistCreateRequest playlistCreateReq =
        new PlaylistCreateRequest(
            faker.funnyName().name(), faker.lorem().paragraph(), faker.bool().bool(), Set.of());
    // When
    webTestClient
        .post()
        .uri("/v2/playlists")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .body(Mono.just(playlistCreateReq), PlaylistCreateRequest.class)
        .exchange()
        .expectStatus()
        .isOk();
    final ResponseSpec actual =
        webTestClient
            .get()
            .uri("/v2/playlists/me")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .exchange();

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
