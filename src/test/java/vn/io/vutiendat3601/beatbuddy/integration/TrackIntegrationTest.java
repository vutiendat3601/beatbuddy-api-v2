package vn.io.vutiendat3601.beatbuddy.integration;

import static vn.io.vutiendat3601.beatbuddy.util.TrackFakerUtils.randomTrackList;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import vn.io.vutiendat3601.beatbuddy.AbstractIntegrationTest;
import vn.io.vutiendat3601.beatbuddy.domain.track.Track;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackRepository;

public class TrackIntegrationTest extends AbstractIntegrationTest {
  private static final int NUM_OF_TRACKS = 10;
  private Track[] tracks;

  @Autowired private TrackRepository trackRepo;

  @BeforeEach
  void setUp() {
    final List<Track> trackList = randomTrackList(NUM_OF_TRACKS);
    trackRepo.saveAll(trackList);
    tracks = trackList.toArray(new Track[NUM_OF_TRACKS]);
  }

  @AfterEach
  void tearDown() {
    trackRepo.deleteAll();
  }

  @Test
  void test() {}

  @Test
  void canGetTrackById() {
    // Given
    final String id = tracks[0].getId();

    // When
    final ResponseSpec actual = webTestClient.get().uri("/v1/tracks/{id}", id).exchange();

    // Then
    actual.expectStatus().isOk();
  }

  @Test
  void canGetTrackByIds() {
    // Given
    final String ids = String.join(",", List.of(tracks).stream().map(Track::getId).toList());

    // When
    final ResponseSpec actual = webTestClient.get().uri("/v1/tracks?ids=" + ids).exchange();

    // Then
    actual.expectStatus().isOk();
  }
}
