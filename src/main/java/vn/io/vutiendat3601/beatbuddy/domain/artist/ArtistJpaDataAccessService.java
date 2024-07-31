package vn.io.vutiendat3601.beatbuddy.domain.artist;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import vn.io.vutiendat3601.beatbuddy.common.model.Page;

@RequiredArgsConstructor
@Repository
public class ArtistJpaDataAccessService implements ArtistDao {
  private final ArtistRepository artistRepo;

  @Override
  @NonNull
  public Optional<Artist> selectById(@NonNull String id) {
    return artistRepo.findById(id);
  }

  @Override
  @NonNull
  public List<Artist> selectByIds(@NonNull List<String> ids) {
    return artistRepo.findAllByIdIn(ids);
  }

  @Override
  @NonNull
  public Page<Artist> selectByKeyword(@NonNull String keyword, int page, int size) {
    final Pageable pageReq = PageRequest.of(page, size);
    final String tokens[] = keyword.split("\s+");
    final String tsvQuery = String.join("&", tokens);

    final org.springframework.data.domain.Page<Artist> artistPage =
        artistRepo.findAllByTsv(tsvQuery, pageReq);
    return Page.from(artistPage);
  }

  @Override
  @NonNull
  public List<Artist> selectTopByTotalLikesDesc(int top) {
    final Pageable pageable = Pageable.ofSize(top);
    return artistRepo.findAllByOrderByTotalLikesDesc(pageable);
  }
}
