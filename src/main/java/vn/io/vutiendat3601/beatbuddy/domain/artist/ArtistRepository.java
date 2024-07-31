package vn.io.vutiendat3601.beatbuddy.domain.artist;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {
  Optional<Artist> findByUrn(String urn);

  Optional<Artist> findById(String id);

  List<Artist> findAllByIdIn(Iterable<String> ids);

  List<Artist> findAllByOrderByTotalLikesDesc(Pageable pageable);

  @Query(
      value =
          """
          SELECT * FROM artist WHERE tsv @@ to_tsquery(:tsvQuery)
          ORDER BY total_likes DESC
          """,
      nativeQuery = true)
  Page<Artist> findAllByTsv(@Param("tsvQuery") String tsvQuery, Pageable pageable);
}
