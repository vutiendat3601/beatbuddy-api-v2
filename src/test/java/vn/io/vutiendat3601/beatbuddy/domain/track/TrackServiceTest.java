package vn.io.vutiendat3601.beatbuddy.domain.track;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static vn.io.vutiendat3601.beatbuddy.util.TrackFakerUtils.randomTrack;
import static vn.io.vutiendat3601.beatbuddy.util.TrackFakerUtils.randomTrackId;
import static vn.io.vutiendat3601.beatbuddy.util.TrackFakerUtils.randomTrackList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.io.vutiendat3601.beatbuddy.common.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.beatbuddy.domain.artist.ArtistDtoMapper;

@ExtendWith(MockitoExtension.class)
public class TrackServiceTest {
  @Mock private TrackDao trackDao;
  private final ArtistDtoMapper artistDtoMapper = new ArtistDtoMapper();
  private final TrackDtoMapper trackDtoMapper = new TrackDtoMapper(artistDtoMapper);

  private TrackService underTest;

  @BeforeEach
  void setUp() {
    underTest = new TrackService(trackDao, trackDtoMapper);
  }

  /* #: getTrackById */
  @Test
  void testGetTrackById() {
    // Given
    final Track track = randomTrack();
    final String id = track.getId();
    when(trackDao.selectById(id)).thenReturn(Optional.of(track));
    final TrackDto expected = trackDtoMapper.apply(track);

    // When
    final TrackDto actual = underTest.getTrackById(id);

    // Then
    assertEquals(expected, actual);
  }

  @Test
  void willThrowWhenTrackNotFound() {
    // Given
    final String id = randomTrackId();
    when(trackDao.selectById(id)).thenReturn(Optional.empty());

    // When & Then
    Executable exec = () -> underTest.getTrackById(id);
    assertThrows(ResourceNotFoundException.class, exec);
  }

  /* # getTrackById */

  /* #: getTrackByIds */

  @Test
  void testGetTrackByIds() {
    // Given
    final int number = 10;
    final List<Track> tracks = randomTrackList(number);
    final List<String> ids = tracks.stream().map(Track::getId).toList();

    final List<TrackDto> expected =
        new LinkedList<>(tracks.stream().map(trackDtoMapper::apply).toList());
    when(trackDao.selectByIds(ids)).thenReturn(tracks);

    // When
    final List<TrackDto> actual = underTest.getTrackByIds(ids);

    // Then
    assertEquals(expected, actual);
  }

  @Test
  void willReturnListOfNullsWhenNoTracksFound() {
    // Given
    final int number = 10;
    final List<String> ids = randomTrackList(number).stream().map(Track::getId).toList();
    when(trackDao.selectByIds(ids)).thenReturn(List.of());
    final List<TrackDto> expected = Arrays.asList(new TrackDto[number]);

    // When
    final List<TrackDto> actual = underTest.getTrackByIds(ids);

    // Then
    assertEquals(expected, actual);
  }

  @Test
  void willReturnListAssureOrderAndSizeIfHaveNulls() {
    // Given
    final int number = 10;
    final List<Track> tracks = randomTrackList(number);
    final List<String> ids = tracks.stream().map(Track::getId).toList();
    final List<TrackDto> expected =
        tracks.stream()
            .map(trackDtoMapper::apply)
            .collect(Collectors.toCollection(LinkedList::new));
    tracks.set(0, null);
    expected.set(0, null);
    when(trackDao.selectByIds(ids)).thenReturn(tracks);

    // When
    final List<TrackDto> actual = underTest.getTrackByIds(ids);

    // Then
    assertEquals(expected, actual);
  }
  /* # getTrackByIds */
}
