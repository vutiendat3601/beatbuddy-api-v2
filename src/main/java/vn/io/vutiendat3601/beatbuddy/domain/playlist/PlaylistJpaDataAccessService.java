package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlaylistJpaDataAccessService implements PlaylistDao {

  private final PlaylistRepository playlistRepo;

  @Override
  @NonNull
  public Optional<Playlist> selectById(@NonNull String id) {
    return playlistRepo.findById(id);
  }

  @Override
  public void insert(@NonNull Playlist playlist) {
    playlistRepo.save(playlist);
  }

  @Override
  public List<Playlist> selectByOwnerId(@NonNull String ownerId) {
    return playlistRepo.findAllByOwnerIdOrderByCreatedAtDesc(ownerId);
  }

  @Override
  public void update(@NonNull Playlist playlist) {
    playlistRepo.save(playlist);
  }
}
