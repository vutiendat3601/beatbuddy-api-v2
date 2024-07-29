package vn.io.vutiendat3601.beatbuddy.domain.artist;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static vn.io.vutiendat3601.beatbuddy.util.ArtistFakerUtils.randomArtist;
import static vn.io.vutiendat3601.beatbuddy.util.ArtistFakerUtils.randomArtistId;
import static vn.io.vutiendat3601.beatbuddy.util.ArtistFakerUtils.randomArtistList;

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
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDao;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDtoMapper;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {
  @Mock private ArtistDao artistDao;
  @Mock private TrackDao trackDao;
  private final ArtistDtoMapper artistDtoMapper = new ArtistDtoMapper();
  private final ArtistDetailsDtoMapper artistDetailsDtoMapper = new ArtistDetailsDtoMapper();
  private final TrackDtoMapper trackDtoMapper = new TrackDtoMapper(artistDtoMapper);
  private ArtistService underTest;

  @BeforeEach
  void setUp() {
    underTest =
        new ArtistService(
            artistDao, trackDao, artistDtoMapper, artistDetailsDtoMapper, trackDtoMapper);
  }

  @Test
  void testGetArtistById() {
    // Given
    final Artist artist = randomArtist();
    final String id = artist.getId();
    when(artistDao.selectById(id)).thenReturn(Optional.of(artist));
    final ArtistDetailsDto expected = artistDetailsDtoMapper.apply(artist);

    // When
    final ArtistDetailsDto actual = underTest.getArtistById(id);

    // Then
    assertEquals(expected, actual);
  }

  @Test
  void willThrowWhenArtistNotFound() {
    // Given
    final String id = randomArtistId();
    when(artistDao.selectById(id)).thenReturn(Optional.empty());

    // When & Then
    final Executable exec = () -> underTest.getArtistById(id);
    assertThrows(ResourceNotFoundException.class, exec);
  }

  /* #: getTrackByIds */

  @Test
  void testGetArtistByIds() {
    // Given
    final int number = 10;
    final List<Artist> tracks = randomArtistList(number);
    final List<String> ids = tracks.stream().map(Artist::getId).toList();

    final List<ArtistDto> expected =
        new LinkedList<>(tracks.stream().map(artistDtoMapper::apply).toList());
    when(artistDao.selectByIds(ids)).thenReturn(tracks);

    // When
    final List<ArtistDto> actual = underTest.getArtistByIds(ids);

    // Then
    assertEquals(expected, actual);
  }

  @Test
  void willReturnListOfNullsWhenNoTracksFound() {
    // Given
    final int number = 10;
    final List<String> ids = randomArtistList(number).stream().map(Artist::getId).toList();
    when(artistDao.selectByIds(ids)).thenReturn(List.of());
    final List<ArtistDto> expected = Arrays.asList(new ArtistDto[number]);

    // When
    final List<ArtistDto> actual = underTest.getArtistByIds(ids);

    // Then
    assertEquals(expected, actual);
  }

  @Test
  void willReturnListAssureOrderAndSizeIfHaveNulls() {
    // Given
    final int number = 10;
    final List<Artist> tracks = randomArtistList(number);
    final List<String> ids = tracks.stream().map(Artist::getId).toList();
    final List<ArtistDto> expected =
        tracks.stream()
            .map(artistDtoMapper::apply)
            .collect(Collectors.toCollection(LinkedList::new));
    tracks.set(0, null);
    expected.set(0, null);
    when(artistDao.selectByIds(ids)).thenReturn(tracks);

    // When
    final List<ArtistDto> actual = underTest.getArtistByIds(ids);

    // Then
    assertEquals(expected, actual);
  }
  /* # getTrackByIds */

}
