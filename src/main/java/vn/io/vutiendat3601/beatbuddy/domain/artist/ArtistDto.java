package vn.io.vutiendat3601.beatbuddy.domain.artist;

import java.util.Set;

public record ArtistDto(
    String id,
    String urn,
    String name,
    Boolean isVerified,
    Boolean isPublic,
    String description,
    String biography,
    String thumbnail,
    String background,
    Set<String> tags,
    Long totalLikes,
    Long totalViews) {}
