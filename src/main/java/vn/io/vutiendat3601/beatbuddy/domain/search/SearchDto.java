package vn.io.vutiendat3601.beatbuddy.domain.search;

import java.util.Set;
import vn.io.vutiendat3601.beatbuddy.common.model.Page;
import vn.io.vutiendat3601.beatbuddy.domain.artist.ArtistDto;
import vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistDto;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDto;

public record SearchDto(
    Page<TrackDto> track,
    Page<ArtistDto> artist,
    Page<PlaylistDto> playlist,
    String keyword,
    Set<SearchType> types,
    int page,
    int size) {}
