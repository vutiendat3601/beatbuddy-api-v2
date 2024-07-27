package vn.io.vutiendat3601.beatbuddy.util;

import java.util.Comparator;
import vn.io.vutiendat3601.beatbuddy.domain.track.Track;

public class TrackTotalLikesComperator implements Comparator<Track> {
  @Override
  public int compare(Track track1, Track track2) {
    final long track1TotalLikes = track1.getTotalLikes() == null ? 0 : track1.getTotalLikes();
    final long track2TotalLikes = track2.getTotalLikes() == null ? 0 : track2.getTotalLikes();
    final int cmp = Long.compare(track1TotalLikes, track2TotalLikes);
    return cmp == 0 ? track1.getName().compareTo(track2.getName()) : cmp;
  }
}
