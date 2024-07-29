package vn.io.vutiendat3601.beatbuddy.domain.track;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import vn.io.vutiendat3601.beatbuddy.common.model.Page;

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

  @Override
  @NonNull
  public Optional<Track> selectByUrn(@NonNull String urn) {
    return trackRepo.findByUrn(urn);
  }

  @Override
  public boolean existsByUrn(@NonNull String urn) {
    return trackRepo.existsByUrn(urn);
  }

  @NonNull
  @Override
  public List<Track> selectByTopTotalLikes(@NonNull Integer top) {
    final Pageable pageable = Pageable.ofSize(top);
    return trackRepo.findAllByOrderByTotalLikesDesc(pageable);
  }

  @NonNull
  @Override
  public List<Track> selectByArtistIdAndTopTotalLikes(
      @NonNull String artistId, @NonNull Integer top) {
    final Pageable pageable = Pageable.ofSize(top);
    return trackRepo.findAllByArtistsIdOrderByTotalLikesDesc(artistId, pageable);
  }

  @Override
  @NonNull
  public List<Track> selectByUrns(@NonNull List<String> urns) {
    return trackRepo.findAllByUrnIn(urns);
  }

  @Override
  @NonNull
  public Page<Track> selectByKeyword(@NonNull String keyword, int page, int size) {
    final Pageable pageReq = Pageable.ofSize(size).withPage(page);
    final String tokens[] = keyword.split("\\s+");
    final String tsvQuery = String.join("&", tokens);
    final org.springframework.data.domain.Page<Track> trackPage =
        trackRepo.findAllByTsv(tsvQuery, pageReq);
    return Page.from(trackPage);
  }
}
