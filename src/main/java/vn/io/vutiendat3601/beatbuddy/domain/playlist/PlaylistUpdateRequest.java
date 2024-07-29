package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import java.util.ArrayList;
import java.util.List;

public record PlaylistUpdateRequest(List<String> itemUrns) {
  public PlaylistUpdateRequest() {
    this(new ArrayList<>());
  }
}
