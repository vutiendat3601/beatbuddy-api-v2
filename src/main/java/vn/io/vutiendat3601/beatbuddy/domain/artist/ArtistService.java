package vn.io.vutiendat3601.beatbuddy.domain.artist;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.beatbuddy.common.exception.ResourceNotFoundException;

@RequiredArgsConstructor
@Service
public class ArtistService {
  private final ArtistDao artistDao;
  private final ArtistDtoMapper artistDtoMapper;

  @NonNull
  public ArtistDto getArtistById(@NonNull String id) {
    return artistDao
        .selectById(id)
        .map(artistDtoMapper::apply)
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
}
