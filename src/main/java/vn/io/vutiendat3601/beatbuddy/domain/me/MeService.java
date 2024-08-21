package vn.io.vutiendat3601.beatbuddy.domain.me;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.beatbuddy.domain.like.Like;
import vn.io.vutiendat3601.beatbuddy.domain.like.LikeDao;
import vn.io.vutiendat3601.beatbuddy.domain.like.LikeDto;
import vn.io.vutiendat3601.beatbuddy.domain.playlist.Playlist;
import vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistDao;
import vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistDto;
import vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistDtoMapper;
import vn.io.vutiendat3601.beatbuddy.util.JwtUtils;

@RequiredArgsConstructor
@Service
public class MeService {
  private final LikeDao likeDao;
  private final PlaylistDao playlistDao;
  private final LikeDtoMapper likeDtoMapper;
  private final PlaylistDtoMapper playlistDtoMapper;

  public LikeDto getLike() {
    final String ownerId = JwtUtils.getSub();
    final Like like =
        likeDao
            .selectByOwnerId(ownerId)
            .orElse(Like.builder().ownerId(ownerId).updatedAt(ZonedDateTime.now()).build());
    return likeDtoMapper.apply(like);
  }

  public List<PlaylistDto> getAllPlaylist() {
    final String ownerId = JwtUtils.getSub();
    final List<Playlist> playlists = playlistDao.selectByOwnerId(ownerId);
    return playlists.stream().map(playlistDtoMapper::apply).toList();
  }
}
