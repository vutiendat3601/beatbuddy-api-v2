package vn.io.vutiendat3601.beatbuddy.domain.artist;

import java.util.List;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDto;

public record ArtistContentPopularityDto(int top, List<TrackDto> tracks) {}
