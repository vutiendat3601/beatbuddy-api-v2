package vn.io.vutiendat3601.beatbuddy.domain.me;

import java.util.function.Function;
import org.springframework.stereotype.Component;
import vn.io.vutiendat3601.beatbuddy.domain.like.Like;
import vn.io.vutiendat3601.beatbuddy.domain.like.LikeDto;

@Component
public class LikeDtoMapper implements Function<Like, LikeDto> {

  @Override
  public LikeDto apply(Like like) {
    return new LikeDto(like.getOwnerId(), like.getUrns(), like.getUpdatedAt());
  }
}
