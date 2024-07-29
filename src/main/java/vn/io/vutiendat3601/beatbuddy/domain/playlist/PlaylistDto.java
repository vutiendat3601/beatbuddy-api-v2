package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public record PlaylistDto(
    String id,
    String urn,
    String name,
    String thumbnail,
    String description,
    Boolean isPublic,
    String ownerId,
    Set<String> tags,
    List<String> itemUrns,
    ZonedDateTime createdAt,
    ZonedDateTime updatedAt) {}
