package vn.io.vutiendat3601.beatbuddy.domain.like;

import java.time.ZonedDateTime;
import java.util.Set;

public record LikeDto(String ownerId, Set<String> urns, ZonedDateTime updatedAt) {}
