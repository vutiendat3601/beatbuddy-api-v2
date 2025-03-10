package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import io.micrometer.common.lang.NonNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaylistRepository extends JpaRepository<Playlist, UUID> {
  @NonNull
  List<Playlist> findAllByOwnerIdOrderByCreatedAtDesc(@NonNull String ownerId);

  @NonNull
  Optional<Playlist> findById(@NonNull String id);

  @NonNull
  @Query(
      value =
          """
          SELECT * FROM playlist WHERE tsv @@ to_tsquery(:tsvQuery)
          """,
      nativeQuery = true)
  Page<Playlist> findAllByTsv(
      @NonNull @Param("tsvQuery") String tsvQuery, @NonNull Pageable pageable);
}
