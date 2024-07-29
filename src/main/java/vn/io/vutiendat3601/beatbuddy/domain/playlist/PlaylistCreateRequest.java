package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import java.util.Set;

public record PlaylistCreateRequest(
    String name, String description, Boolean isPublic, Set<String> tags) {}
