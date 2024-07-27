package vn.io.vutiendat3601.beatbuddy.domain.artist;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {
  Optional<Artist> findByUrn(String urn);

  Optional<Artist> findById(String id);

  List<Artist> findAllByIdIn(Iterable<String> ids);
}
