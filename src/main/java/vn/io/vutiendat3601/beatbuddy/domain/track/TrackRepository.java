package vn.io.vutiendat3601.beatbuddy.domain.track;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface TrackRepository extends JpaRepository<Track, UUID> {
  @NonNull
  Optional<Track> findByUrn(@NonNull String urn);

  @NonNull
  Optional<Track> findById(@NonNull String id);

  @NonNull
  List<Track> findAllByIdIn(@NonNull Iterable<String> ids);

  boolean existsByUrn(@NonNull String urn);

  @NonNull
  List<Track> findAllByUrnIn(@NonNull List<String> urns);

  @NonNull
  List<Track> findAllByOrderByTotalLikesDesc(@NonNull Pageable pageable);

  @NonNull
  List<Track> findAllByArtistsIdOrderByTotalLikesDesc(
      @NonNull String artistId, @NonNull Pageable pageable);

  @NonNull
  @Query(
      value =
          """
          SELECT * FROM track WHERE tsv @@ to_tsquery(:tsvQuery)
          ORDER BY total_likes DESC
          """,
      nativeQuery = true)
  Page<Track> findAllByTsv(@NonNull @Param("tsvQuery") String tsvQuery, @NonNull Pageable pageable);
}
