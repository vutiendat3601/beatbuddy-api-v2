package vn.io.vutiendat3601.beatbuddy.domain.track;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TrackJpaDataAccessService implements TrackDao {
  private final TrackRepository trackRepo;

  @Override
  @NonNull
  public Optional<Track> selectById(@NonNull String id) {
    return trackRepo.findById(id);
  }

  @Override
  @NonNull
  public List<Track> selectByIds(@NonNull List<String> ids) {
    return trackRepo.findAllByIdIn(ids);
  }
}
