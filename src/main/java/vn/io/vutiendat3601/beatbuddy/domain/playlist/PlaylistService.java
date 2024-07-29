package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import static vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistConstant.PLAYLIST_ID_LENGTH;
import static vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistConstant.PLAYLIST_URN_PREFIX;
import static vn.io.vutiendat3601.beatbuddy.domain.track.TrackConstant.TRACK_URN_PREFIX;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.beatbuddy.common.exception.RequestValidationException;
import vn.io.vutiendat3601.beatbuddy.common.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.beatbuddy.domain.track.Track;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDao;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDto;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDtoMapper;
import vn.io.vutiendat3601.beatbuddy.util.StringUtils;

@RequiredArgsConstructor
@Service
public class PlaylistService {
  private final PlaylistDao playlistDao;
  private final TrackDao trackDao;
  private final PlaylistDtoMapper playlistDtoMapper;
  private final TrackDtoMapper trackDtoMapper;

  public void createPlaylist(@NonNull PlaylistCreateRequest playlistCreateReq) {
    final String ownerId = "1a6896a1-225f-4bc2-a7bb-17db09b7dfa4"; // TODO: change this hard code
    final String id = StringUtils.randomString(PLAYLIST_ID_LENGTH);
    final String urn = PLAYLIST_URN_PREFIX + id;
    final Set<String> unaccentTags =
        playlistCreateReq.tags().stream()
            .map(StringUtils::removeAccent)
            .collect(Collectors.toSet());
    final Playlist playlist =
        Playlist.builder()
            .id(id)
            .urn(urn)
            .name(playlistCreateReq.name())
            .description(playlistCreateReq.description())
            .isPublic(playlistCreateReq.isPublic())
            .tags(unaccentTags)
            .ownerId(ownerId)
            .build();
    playlistDao.insert(playlist);
  }

  public void updatePlaylist(@NonNull String id, @NonNull PlaylistUpdateRequest playlistUpdateReq) {
    final Playlist playlist =
        playlistDao
            .selectById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Playlist not found: [id=%s]".formatted(id)));
    boolean isChanged = false;
    if (playlistUpdateReq.itemUrns() != null
        && !playlistUpdateReq.itemUrns().equals(playlist.getItemUrns())) {
      for (String itemUrn : playlistUpdateReq.itemUrns()) {
        if (itemUrn.startsWith(TRACK_URN_PREFIX) && !trackDao.existsByUrn(itemUrn)) {
          throw new ResourceNotFoundException("Track not found: [urn=%s]".formatted(itemUrn));
        }
      }
      playlist.setItemUrns(playlistUpdateReq.itemUrns());
      isChanged = true;
    }
    if (!isChanged) {
      throw new RequestValidationException("No changes found");
    }
    playlistDao.update(playlist);
  }

  public List<PlaylistDto> getAllPlaylistByCurrentUser() {
    final String ownerId = "1a6896a1-225f-4bc2-a7bb-17db09b7dfa4"; // TODO: change this hard code
    final List<Playlist> playlists = playlistDao.selectByOwnerId(ownerId);
    return playlists.stream().map(playlistDtoMapper::apply).toList();
  }

  @NonNull
  public PlaylistDetailsDto getPlaylistDetailsById(@NonNull String id) {
    final Playlist playlist =
        playlistDao
            .selectById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Playlist not found: [id=%s]".formatted(id)));
    return mapToPlaylistDetailsDto(playlist);
  }

  @NonNull
  private PlaylistDetailsDto mapToPlaylistDetailsDto(@NonNull Playlist playlist) {
    final List<String> trackUrns =
        playlist.getItemUrns().stream().filter(iu -> iu.startsWith(TRACK_URN_PREFIX)).toList();
    final List<Track> tracks = trackDao.selectByUrns(trackUrns);
    final Map<String, TrackDto> trackDtoMap =
        tracks.stream().map(trackDtoMapper).collect(Collectors.toMap(TrackDto::urn, t -> t));
    final List<TrackDto> trackDtos = new LinkedList<>();
    for (String urn : playlist.getItemUrns()) {
      TrackDto trackDto = null;
      if (trackDtoMap.containsKey(urn)) {
        trackDto = trackDtoMap.get(urn);
      }
      trackDtos.add(trackDto);
    }
    return new PlaylistDetailsDto(
        playlist.getId(),
        playlist.getUrn(),
        playlist.getName(),
        playlist.getThumbnail(),
        playlist.getDescription(),
        playlist.getIsPublic(),
        playlist.getOwnerId(),
        playlist.getTags(),
        trackDtos,
        playlist.getCreatedAt(),
        playlist.getUpdatedAt());
  }
}
