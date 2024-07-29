package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, UUID> {
  List<Playlist> findAllByOwnerIdOrderByCreatedAtDesc(String ownerId);

  Optional<Playlist> findById(String id);
}
