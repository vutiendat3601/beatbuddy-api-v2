package vn.io.vutiendat3601.beatbuddy.domain.artist;

import io.micrometer.common.lang.NonNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {
  @NonNull
  Optional<Artist> findByUrn(@NonNull String urn);

  @NonNull
  Optional<Artist> findById(@NonNull String id);

  @NonNull
  List<Artist> findAllByIdIn(@NonNull Iterable<String> ids);

  @NonNull
  List<Artist> findAllByOrderByTotalLikesDesc(@NonNull Pageable pageable);

  @NonNull
  @Query(
      value =
          """
          SELECT * FROM artist WHERE tsv @@ to_tsquery(:tsvQuery)
          ORDER BY total_likes DESC
          """,
      nativeQuery = true)
  Page<Artist> findAllByTsv(
      @NonNull @Param("tsvQuery") String tsvQuery, @NonNull Pageable pageable);
}
