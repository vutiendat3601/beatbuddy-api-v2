package vn.io.vutiendat3601.beatbuddy.domain.artist;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;

public interface ArtistDao {

  @NonNull
  Optional<Artist> selectById(@NonNull String id);

  @NonNull
  List<Artist> selectByIds(@NonNull List<String> ids);
}
