package vn.io.vutiendat3601.beatbuddy.domain.artist;

import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ArtistDtoMapper implements Function<Artist, ArtistDto> {

  @Override
  public ArtistDto apply(Artist artist) {
    Assert.notNull(artist, "artist must not be null");
    return new ArtistDto(
        artist.getId(),
        artist.getUrn(),
        artist.getName(),
        artist.getIsVerified(),
        artist.getIsPublic(),
        artist.getRealName(),
        artist.getBirthDate(),
        artist.getDescription(),
        artist.getNationality(),
        artist.getBiography(),
        artist.getThumbnail(),
        artist.getBackground(),
        artist.getTags(),
        artist.getTotalLikes(),
        artist.getTotalViews());
  }
}
