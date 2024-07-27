package vn.io.vutiendat3601.beatbuddy.util;

import java.util.Comparator;
import vn.io.vutiendat3601.beatbuddy.domain.artist.Artist;

public class ArtistTotalLikesComparator implements Comparator<Artist> {
  @Override
  public int compare(Artist track1, Artist track2) {
    final long artist1TotalLikes = track1.getTotalLikes() == null ? 0 : track1.getTotalLikes();
    final long artist2TotalLikes = track2.getTotalLikes() == null ? 0 : track2.getTotalLikes();
    final int cmp = -Long.compare(artist1TotalLikes, artist2TotalLikes);
    return cmp == 0 ? track1.getName().compareTo(track2.getName()) : cmp;
  }
}
