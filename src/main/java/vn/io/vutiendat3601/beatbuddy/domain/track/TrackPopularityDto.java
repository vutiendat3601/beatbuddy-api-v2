package vn.io.vutiendat3601.beatbuddy.domain.track;

import java.util.List;

public record TrackPopularityDto(int top, List<TrackDto> tracks) {}
