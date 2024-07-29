package vn.io.vutiendat3601.beatbuddy.domain.track;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import vn.io.vutiendat3601.beatbuddy.common.model.Page;

public interface TrackDao {
  @NonNull
  Optional<Track> selectById(@NonNull String id);

  @NonNull
  List<Track> selectByIds(@NonNull List<String> ids);

  @NonNull
  List<Track> selectByUrns(@NonNull List<String> urns);

  @NonNull
  Optional<Track> selectByUrn(@NonNull String urn);

  @NonNull
  Page<Track> selectByKeyword(@NonNull String keyword, int page, int size);

  boolean existsByUrn(@NonNull String urn);

  @NonNull
  List<Track> selectByTopTotalLikes(@NonNull Integer top);

  @NonNull
  List<Track> selectByArtistIdAndTopTotalLikes(@NonNull String artistId, @NonNull Integer top);
}
