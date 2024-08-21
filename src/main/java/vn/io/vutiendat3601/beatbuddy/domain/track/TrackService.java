package vn.io.vutiendat3601.beatbuddy.domain.track;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.beatbuddy.common.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.beatbuddy.domain.like.Like;
import vn.io.vutiendat3601.beatbuddy.domain.like.LikeDao;
import vn.io.vutiendat3601.beatbuddy.util.JwtUtils;

@RequiredArgsConstructor
@Service
public class TrackService {
  private final TrackDao trackDao;
  private final LikeDao likeDao;
  private final TrackDtoMapper trackDtoMapper;
  private final TrackDetailsDtoMapper trackDetailsDtoMapper;

  @NonNull
  public TrackDetailsDto getTrackDetailsById(@NonNull String id) {
    return trackDao
        .selectById(id)
        .map(trackDetailsDtoMapper::apply)
        .orElseThrow(() -> new ResourceNotFoundException("Track not found: [id=%s]".formatted(id)));
  }

  @NonNull
  public TrackDto getTrackByUrn(@NonNull String urn) {
    return trackDao
        .selectByUrn(urn)
        .map(trackDtoMapper::apply)
        .orElseThrow(
            () -> new ResourceNotFoundException("Track not found: [urn=%s]".formatted(urn)));
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

  public TrackPopularityDto getPopularTrack(int top) {
    final List<Track> tracks = trackDao.selectTopByTotalLikesDesc(top);
    final List<TrackDto> trackDtos = tracks.stream().map(trackDtoMapper::apply).toList();
    return new TrackPopularityDto(top, trackDtos);
  }

  public void likeTrack(@NonNull TrackLikeRequest trackLikeReq) {
    final String id = trackLikeReq.id();
    final Track track =
        trackDao
            .selectById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Track not found: [id=%s]".formatted(id)));
    final String ownerId =
        Optional.ofNullable(JwtUtils.getSub())
            .orElseThrow(() -> new AccessDeniedException("No authentication provided"));
    likeDao
        .selectByOwnerId(ownerId)
        .ifPresentOrElse(
            like -> {
              like.getUrns().add(track.getUrn());
              likeDao.update(like);
            },
            () -> {
              final Like like = new Like();
              like.setOwnerId(ownerId);
              like.getUrns().add(track.getUrn());
              likeDao.insert(like);
            });
  }
}
