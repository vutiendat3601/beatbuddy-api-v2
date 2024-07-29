package vn.io.vutiendat3601.beatbuddy.domain.track;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrackRepository extends JpaRepository<Track, UUID> {
  Optional<Track> findByUrn(String urn);

  Optional<Track> findById(String id);

  List<Track> findAllByIdIn(Iterable<String> ids);

  boolean existsByUrn(String urn);

  List<Track> findAllByUrnIn(List<String> urns);

  List<Track> findAllByOrderByTotalLikesDesc(Pageable pageable);

  List<Track> findAllByArtistsIdOrderByTotalLikesDesc(String artistId, Pageable pageable);

  @Query(
      value =
          """
          SELECT * FROM track WHERE tsv @@ to_tsquery(:tsvQuery)
          ORDER BY total_likes DESC
          """,
      nativeQuery = true)
  Page<Track> findAllByTsv(@Param("tsvQuery") String tsvQuery, Pageable pageable);
}
