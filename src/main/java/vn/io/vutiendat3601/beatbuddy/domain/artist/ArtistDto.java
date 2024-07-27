package vn.io.vutiendat3601.beatbuddy.domain.artist;

import java.time.LocalDate;

public record ArtistDto(
    String id,
    String urn,
    String name,
    Boolean isVerified,
    Boolean isPublic,
    String realName,
    LocalDate birthDate,
    String description,
    String nationality,
    String biography,
    String thumbnail,
    String background,
    String tags,
    Long totalLikes,
    Long totalViews) {}
