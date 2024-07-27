package vn.io.vutiendat3601.beatbuddy.domain.track;

import java.util.List;
import vn.io.vutiendat3601.beatbuddy.domain.artist.ArtistDto;

public record TrackDto(
    String id,
    String urn,
    String name,
    Integer durationSec,
    String description,
    String releasedDate,
    String thumbnail,
    Boolean isPublic,
    Boolean isPlayable,
    Long totalLikes,
    Long totalViews,
    Long totalListens,
    String tags,
    List<ArtistDto> artists) {}
