package vn.io.vutiendat3601.beatbuddy.domain.track;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

  @Override
  public Optional<Track> selectByUrn(String urn) {
    return trackRepo.findByUrn(urn);
  }

  @Override
  public boolean existsByUrn(String urn) {
    return trackRepo.existsByUrn(urn);
  }

  @Override
  public List<Track> selectByTopTotalLikes(Integer top) {
    final Pageable pageable = Pageable.ofSize(top);
    return trackRepo.findAllByOrderByTotalLikesDesc(pageable);
  }

  @Override
  public List<Track> selectByArtistIdAndTopTotalLikes(String artistId, Integer top) {
    final Pageable pageable = Pageable.ofSize(top);
    return trackRepo.findAllByArtistsIdOrderByTotalLikesDesc(artistId, pageable);
  }

  @Override
  @NonNull
  public List<Track> selectByUrns(@NonNull List<String> urns) {
    return trackRepo.findAllByUrnIn(urns);
  }
}
