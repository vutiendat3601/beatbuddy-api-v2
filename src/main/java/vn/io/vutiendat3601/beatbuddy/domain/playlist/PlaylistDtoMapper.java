package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class PlaylistDtoMapper implements Function<Playlist, PlaylistDto> {

  @Override
  public PlaylistDto apply(Playlist playlist) {
    Assert.notNull(playlist, "playlist must not be null");
    return new PlaylistDto(
        playlist.getId(),
        playlist.getUrn(),
        playlist.getName(),
        playlist.getThumbnail(),
        playlist.getDescription(),
        playlist.getIsPublic(),
        playlist.getOwnerId(),
        playlist.getTags(),
        playlist.getItemUrns(),
        playlist.getCreatedAt(),
        playlist.getUpdatedAt());
  }
}
