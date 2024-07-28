package vn.io.vutiendat3601.beatbuddy.domain.track;

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
public class TrackService {
  private final TrackDao trackDao;
  private final TrackDtoMapper trackDtoMapper;

  @NonNull
  public TrackDto getTrackById(@NonNull String id) {
    return trackDao
        .selectById(id)
        .map(trackDtoMapper::apply)
        .orElseThrow(() -> new ResourceNotFoundException("Track not found: [id=%s]".formatted(id)));
  }

  @NonNull
  public List<TrackDto> getTrackByIds(@NonNull List<String> ids) {
    final List<Track> tracks = trackDao.selectByIds(ids);
    final Map<String, TrackDto> trackMap =
        tracks.stream()
            .filter(t -> t != null)
            .collect(Collectors.toMap(Track::getId, trackDtoMapper::apply));
    final List<TrackDto> trackDtos = new LinkedList<>();
    ids.forEach(id -> trackDtos.add(trackMap.get(id)));
    return trackDtos;
  }
}
