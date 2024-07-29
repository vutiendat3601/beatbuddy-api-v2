package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDto;

public record PlaylistDetailsDto(
    String id,
    String urn,
    String name,
    String thumbnail,
    String description,
    Boolean isPublic,
    String ownerId,
    Set<String> tags,
    List<TrackDto> tracks,
    ZonedDateTime createdAt,
    ZonedDateTime updatedAt) {}
