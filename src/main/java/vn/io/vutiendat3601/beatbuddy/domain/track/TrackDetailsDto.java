package vn.io.vutiendat3601.beatbuddy.domain.track;

import java.util.List;
import java.util.Set;
import vn.io.vutiendat3601.beatbuddy.domain.artist.ArtistDto;

public record TrackDetailsDto(
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
    Set<String> tags,
    List<ArtistDto> artists,
    String fileM3u8) {}
