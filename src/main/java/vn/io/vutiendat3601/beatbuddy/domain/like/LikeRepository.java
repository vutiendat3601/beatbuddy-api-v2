package vn.io.vutiendat3601.beatbuddy.domain.like;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface LikeRepository extends JpaRepository<Like, UUID> {
  @NonNull
  Optional<Like> findByOwnerId(@NonNull String ownerId);
}
