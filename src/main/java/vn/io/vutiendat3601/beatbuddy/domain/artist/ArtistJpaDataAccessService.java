package vn.io.vutiendat3601.beatbuddy.domain.artist;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ArtistJpaDataAccessService implements ArtistDao {
  private final ArtistRepository artistRepo;

  @Override
  @NonNull
  public Optional<Artist> selectById(@NonNull String id) {
    return artistRepo.findById(id);
  }

  @Override
  @NonNull
  public List<Artist> selectByIds(@NonNull List<String> ids) {
    return artistRepo.findAllByIdIn(ids);
  }
}
