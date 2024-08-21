package vn.io.vutiendat3601.beatbuddy.domain.like;

import java.util.Optional;
import org.springframework.lang.NonNull;

public interface LikeDao {
  Optional<Like> selectByOwnerId(@NonNull String ownerId);

  void insert(@NonNull Like like);

  void update(@NonNull Like like);
}
