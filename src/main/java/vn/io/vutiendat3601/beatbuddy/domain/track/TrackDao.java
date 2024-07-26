package vn.io.vutiendat3601.beatbuddy.domain.track;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;

public interface TrackDao {
  @NonNull
  Optional<Track> selectById(@NonNull String id);

  @NonNull
  List<Track> selectByIds(@NonNull List<String> ids);
}
