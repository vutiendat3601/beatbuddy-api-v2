package vn.io.vutiendat3601.beatbuddy.domain.artist;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import vn.io.vutiendat3601.beatbuddy.common.model.Page;

public interface ArtistDao {

  @NonNull
  Optional<Artist> selectById(@NonNull String id);

  @NonNull
  List<Artist> selectByIds(@NonNull List<String> ids);

  @NonNull
  Page<Artist> selectByKeyword(@NonNull String keyword, int page, int size);

  @NonNull
  List<Artist> selectTopByTotalLikesDesc(int top);
}
