package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.beatbuddy.common.model.Page;

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

  @NonNull
  @Override
  public List<Playlist> selectByOwnerId(@NonNull String ownerId) {
    return playlistRepo.findAllByOwnerIdOrderByCreatedAtDesc(ownerId);
  }

  @Override
  public void update(@NonNull Playlist playlist) {
    playlistRepo.save(playlist);
  }

  @Override
  @NonNull
  public Page<Playlist> selectByKeyword(@NonNull String keyword, int page, int size) {
    final Pageable pageReq = PageRequest.of(page, size);
    final String tokens[] = keyword.split("\s+");
    final String tsvQuery = String.join("&", tokens);

    final org.springframework.data.domain.Page<Playlist> playlistPage =
        playlistRepo.findAllByTsv(tsvQuery, pageReq);
    return Page.from(playlistPage);
  }
}
