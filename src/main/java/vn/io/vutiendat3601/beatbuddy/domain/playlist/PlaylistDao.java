package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import vn.io.vutiendat3601.beatbuddy.common.model.Page;

public interface PlaylistDao {
  @NonNull
  Optional<Playlist> selectById(@NonNull String id);

  void insert(@NonNull Playlist playlist);

  void update(@NonNull Playlist playlist);

  @NonNull
  List<Playlist> selectByOwnerId(@NonNull String ownerId);

  @NonNull
  Page<Playlist> selectByKeyword(@NonNull String keyword, int page, int size);
}
