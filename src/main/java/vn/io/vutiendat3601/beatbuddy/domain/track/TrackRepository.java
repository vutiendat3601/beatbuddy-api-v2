package vn.io.vutiendat3601.beatbuddy.domain.track;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, UUID> {
  Optional<Track> findByUrn(String urn);

  Optional<Track> findById(String id);

  List<Track> findAllByIdIn(Iterable<String> ids);

  boolean existsByUrn(String urn);

  List<Track> findAllByUrnIn(List<String> urns);

  List<Track> findAllByOrderByTotalLikesDesc(Pageable pageable);

  List<Track> findAllByArtistsIdOrderByTotalLikesDesc(String artistId, Pageable pageable);
}
