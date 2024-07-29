package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;

public interface PlaylistDao {
  @NonNull
  Optional<Playlist> selectById(@NonNull String id);

  void insert(@NonNull Playlist playlist);

  void update(@NonNull Playlist playlist);

  List<Playlist> selectByOwnerId(@NonNull String ownerId);
}
