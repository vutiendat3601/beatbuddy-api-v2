package vn.io.vutiendat3601.beatbuddy.domain.artist;

import static org.mockito.Mockito.verify;
import static vn.io.vutiendat3601.beatbuddy.util.ArtistFakerUtils.randomArtistId;
import static vn.io.vutiendat3601.beatbuddy.util.ArtistFakerUtils.randomArtistIds;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArtistJpaDataAccessServiceTest {
  @Mock private ArtistRepository artistRepo;

  private ArtistJpaDataAccessService underTest;

  @BeforeEach
  void setUp() {
    underTest = new ArtistJpaDataAccessService(artistRepo);
  }

  @Test
  void testSelectById() {
    // Given
    final String id = randomArtistId();

    // When
    underTest.selectById(id);

    // Then
    verify(artistRepo).findById(id);
  }

  @Test
  void testSelectByIds() {
    // Given
    final List<String> ids = randomArtistIds(10);

    // When
    underTest.selectByIds(ids);

    // Then
    verify(artistRepo).findAllByIdIn(ids);
  }
}
