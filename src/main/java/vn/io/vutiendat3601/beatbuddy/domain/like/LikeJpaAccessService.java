package vn.io.vutiendat3601.beatbuddy.domain.like;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LikeJpaAccessService implements LikeDao {
  private final LikeRepository likeRepo;

  @Override
  public Optional<Like> selectByOwnerId(@NonNull String ownerId) {
    return likeRepo.findByOwnerId(ownerId);
  }

  @Override
  public void insert(@NonNull Like like) {
    likeRepo.save(like);
  }

  @Override
  public void update(@NonNull Like like) {
    likeRepo.save(like);
  }
}
