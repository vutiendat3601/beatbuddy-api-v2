package vn.io.vutiendat3601.beatbuddy.domain.track;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static vn.io.vutiendat3601.beatbuddy.util.ArtistFakerUtils.randomArtist;
import static vn.io.vutiendat3601.beatbuddy.util.TrackFakerUtils.randomTrack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import vn.io.vutiendat3601.beatbuddy.domain.artist.Artist;
import vn.io.vutiendat3601.beatbuddy.domain.artist.ArtistDtoMapper;

public class TrackDtoMapperTest {
  private final ArtistDtoMapper artistDtoMapper = new ArtistDtoMapper();
  private final TrackDtoMapper trackDtoMapper = new TrackDtoMapper(artistDtoMapper);

  @Test
  void testApply() {
    // Given
    final Track track = randomTrack();
    for (int i = 0; i < 3; i++) {
      final Artist artist = randomArtist();
      track.getArtists().add(artist);
    }
    final TrackDto expected =
        new TrackDto(
            track.getId(),
            track.getUrn(),
            track.getName(),
            track.getDurationSec(),
            track.getDescription(),
            track.getReleasedDate(),
            track.getThumbnail(),
            track.getIsPublic(),
            track.getIsPlayable(),
            track.getTotalLikes(),
            track.getTotalViews(),
            track.getTotalListens(),
            track.getTags(),
            track.getArtists().stream().map(artistDtoMapper::apply).toList());
    // When
    final TrackDto actual = trackDtoMapper.apply(track);

    // Then
    assertEquals(expected, actual);
  }

  @Test
  void willThrowWhenTrackIsNull() {
    // Given
    final Track track = null;

    // When & Then
    final Executable exec = () -> trackDtoMapper.apply(track);
    assertThrows(IllegalArgumentException.class, exec);
  }

  @Test
  void willThrowWhenArtistsListIsNull() {
    // Given
    final Track track = randomTrack();
    track.setArtists(null);

    // When & Then
    final Executable exec = () -> trackDtoMapper.apply(track);
    assertThrows(IllegalArgumentException.class, exec);
  }

  @Test
  void willSuccessWhenArtistsListContainNull() {
    // Given
    final Track track = randomTrack();
    track.getArtists().add(null);
    final TrackDto expected =
        new TrackDto(
            track.getId(),
            track.getUrn(),
            track.getName(),
            track.getDurationSec(),
            track.getDescription(),
            track.getReleasedDate(),
            track.getThumbnail(),
            track.getIsPublic(),
            track.getIsPlayable(),
            track.getTotalLikes(),
            track.getTotalViews(),
            track.getTotalListens(),
            track.getTags(),
            track.getArtists().stream()
                .filter(a -> a != null)
                .map(artistDtoMapper::apply)
                .toList());

    // When
    final TrackDto actual = trackDtoMapper.apply(track);

    // Then
    assertEquals(expected, actual);
  }
}
