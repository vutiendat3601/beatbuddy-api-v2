package vn.io.vutiendat3601.beatbuddy.domain.track;

import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import vn.io.vutiendat3601.beatbuddy.domain.artist.ArtistDto;
import vn.io.vutiendat3601.beatbuddy.domain.artist.ArtistDtoMapper;

@RequiredArgsConstructor
@Component
public class TrackDetailsDtoMapper implements Function<Track, TrackDetailsDto> {
  private final ArtistDtoMapper artistDtoMapper;

  @Override
  @NonNull
  public TrackDetailsDto apply(Track track) {
    Assert.notNull(track, "track must not be null");
    Assert.notNull(track.getArtists(), "track.artists must not be null");
    final List<ArtistDto> artists =
        track.getArtists().stream().filter(a -> a != null).map(artistDtoMapper::apply).toList();
    return new TrackDetailsDto(
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
        artists,
        track.getFileM3u8());
  }
}
