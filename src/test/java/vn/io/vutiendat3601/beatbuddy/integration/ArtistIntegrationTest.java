package vn.io.vutiendat3601.beatbuddy.integration;

import static vn.io.vutiendat3601.beatbuddy.util.ArtistFakerUtils.randomArtistList;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import vn.io.vutiendat3601.beatbuddy.AbstractIntegrationTest;
import vn.io.vutiendat3601.beatbuddy.domain.artist.Artist;
import vn.io.vutiendat3601.beatbuddy.domain.artist.ArtistDto;
import vn.io.vutiendat3601.beatbuddy.domain.artist.ArtistDtoMapper;
import vn.io.vutiendat3601.beatbuddy.domain.artist.ArtistRepository;

public class ArtistIntegrationTest extends AbstractIntegrationTest {
  private static final int NUM_OF_ARTISTS = 10;
  private Artist[] artists;

  @Autowired private ArtistRepository artistRepo;

  @Autowired private ArtistDtoMapper artistDtoMapper;

  @BeforeEach
  void setUp() {
    final List<Artist> artistList = randomArtistList(NUM_OF_ARTISTS);
    artistRepo.saveAll(artistList);
    artists = artistList.toArray(new Artist[NUM_OF_ARTISTS]);
  }

  @AfterEach
  void tearDown() {
    artistRepo.deleteAll();
  }

  @Test
  void canGetArtistById() {
    // Given
    final ArtistDto artistDto = artistDtoMapper.apply(artists[0]);
    final String id = artists[0].getId();

    // When
    final ResponseSpec actual = webTestClient.get().uri("/v2/artists/{id}", id).exchange();

    // Then
    actual.expectStatus().isOk();
    actual.expectBody(new ParameterizedTypeReference<ArtistDto>() {}).isEqualTo(artistDto);
  }

  @Test
  void canGetTrackByIds() {
    // Given
    final List<ArtistDto> expected = List.of(artists).stream().map(artistDtoMapper).toList();
    final String ids = String.join(",", List.of(artists).stream().map(Artist::getId).toList());

    // When
    final ResponseSpec actual = webTestClient.get().uri("/v2/artists?ids=" + ids).exchange();

    // Then
    actual.expectStatus().isOk();
    actual.expectBody(new ParameterizedTypeReference<List<ArtistDto>>() {}).isEqualTo(expected);
  }
}
