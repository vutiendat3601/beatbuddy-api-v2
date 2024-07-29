package vn.io.vutiendat3601.beatbuddy.domain.artist;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.beatbuddy.common.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.beatbuddy.domain.track.Track;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDao;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDto;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDtoMapper;

@RequiredArgsConstructor
@Service
public class ArtistService {
  private final ArtistDao artistDao;
  private final TrackDao trackDao;
  private final ArtistDtoMapper artistDtoMapper;
  private final ArtistDetailsDtoMapper artistDetailsDtoMapper;
  private final TrackDtoMapper trackDtoMapper;

  @NonNull
  public ArtistDetailsDto getArtistById(@NonNull String id) {
    return artistDao
        .selectById(id)
        .map(artistDetailsDtoMapper::apply)
        .orElseThrow(
            () -> new ResourceNotFoundException("Artist not found: [id=%s]".formatted(id)));
  }

  @NonNull
  public List<ArtistDto> getArtistByIds(@NonNull List<String> ids) {
    final List<Artist> artists = artistDao.selectByIds(ids);
    final Map<String, ArtistDto> artistMap =
        artists.stream()
            .filter(a -> a != null)
            .collect(Collectors.toMap(Artist::getId, artistDtoMapper::apply));
    final List<ArtistDto> artistDtos = new LinkedList<>();
    ids.forEach(id -> artistDtos.add(artistMap.get(id)));
    return artistDtos;
  }

  @NonNull
  public List<TrackDto> getPopularTrack(@NonNull String artistId, @NonNull Integer top) {
    final List<Track> artists = trackDao.selectByArtistIdAndTopTotalLikes(artistId, top);
    return artists.stream().map(trackDtoMapper::apply).toList();
  }
}
