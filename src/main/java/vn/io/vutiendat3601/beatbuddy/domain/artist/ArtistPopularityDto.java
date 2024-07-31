package vn.io.vutiendat3601.beatbuddy.domain.artist;

import java.util.List;

public record ArtistPopularityDto(int top, List<ArtistDto> artists) {}
