package vn.io.vutiendat3601.beatbuddy.domain.track;

import static org.mockito.Mockito.verify;
import static vn.io.vutiendat3601.beatbuddy.util.TrackFakerUtils.randomTrackId;
import static vn.io.vutiendat3601.beatbuddy.util.TrackFakerUtils.randomTrackIds;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TrackJpaDataAccessServiceTest {
  @Mock private TrackRepository trackRepo;

  private TrackJpaDataAccessService underTest;

  @BeforeEach
  void setUp() {
    underTest = new TrackJpaDataAccessService(trackRepo);
  }

  /* #: selectedById(id) */
  @Test
  void testSelectById() {
    // Given
    final String id = randomTrackId();

    // When
    underTest.selectById(id);

    // Then
    verify(trackRepo).findById(id);
  }

  /* # selectedById(id) */

  /* # selectedByIds(ids) */
  @Test
  void testSelectByIds() {
    // Given
    final List<String> ids = randomTrackIds(10);

    // When
    underTest.selectByIds(ids);

    // Then
    verify(trackRepo).findAllByIdIn(ids);
  }
  /* # selectedByIds(ids) */
}
