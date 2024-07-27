package vn.io.vutiendat3601.beatbuddy.util;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import vn.io.vutiendat3601.beatbuddy.domain.artist.Artist;
import vn.io.vutiendat3601.beatbuddy.domain.track.Track;

public class TrackFakerUtils {
  private static final DateTimeFormatter isoDateFormatter =
      DateTimeFormatter.ofPattern("YYYY-MM-dd");
  private static final Faker FAKER = new Faker();
  private static final RandomService RANDOM = FAKER.random();
  private static final String[] REF_CODE_PREFIXES = {"spt_", "ytb_", "zmp3_"};

  public static final Track randomTrack() {
    return randomTrackList(1).get(0);
  }

  public static String randomTrackId() {
    return randomTrack().getId();
  }

  public static List<String> randomTrackIds(int number) {
    return randomTrackList(number).stream().map(Track::getId).toList();
  }

  public static Map<String, Track> randomTrackMap(int number) {
    return randomTrackList(number).stream().collect(Collectors.toMap(Track::getId, t -> t));
  }

  public static List<Track> randomTrackList(int number) {
    final List<Track> tracks = new ArrayList<>();
    final List<Artist> mockArtists =
        ArtistFakerUtils.randomArtistList(number > 100 ? 100 : number / 2 + 1);
    for (int i = 0; i < number; i++) {
      final int numOfArtists = RANDOM.nextInt(3);
      final SortedSet<Artist> artistSet = new TreeSet<>(new ArtistTotalLikesComparator());
      for (int j = 0; j < numOfArtists; j++) {
        final int k = RANDOM.nextInt(mockArtists.size());
        artistSet.add(mockArtists.get(k));
      }
      final List<Artist> artists = new ArrayList<>(artistSet);
      final String id = RANDOM.hex(16).toLowerCase();
      final String urn = "beatbuddy:track:" + id;
      final Integer durationSec = RANDOM.nextInt(1000) > 10 ? RANDOM.nextInt(1000) : null;
      final String releasedDate =
          RANDOM.nextBoolean()
              ? FAKER
                  .date()
                  .birthday()
                  .toInstant()
                  .atZone(ZoneId.systemDefault())
                  .format(isoDateFormatter)
              : null;
      final String name = FAKER.funnyName().name();
      final String description = RANDOM.nextBoolean() ? FAKER.lorem().sentence() : null;
      final String thumbnail =
          RANDOM.nextBoolean()
              ? "https://%s/files/%s.jpg".formatted(FAKER.internet().url(), id)
              : null;
      final String fileM3u8 =
          RANDOM.nextBoolean()
              ? "https://%s/files/%s.m3u8".formatted(FAKER.internet().url(), id)
              : null;
      final String refCode =
          REF_CODE_PREFIXES[RANDOM.nextInt(REF_CODE_PREFIXES.length)]
              + RANDOM.hex(RANDOM.nextInt(10, 32));
      final String createdBy = UUID.randomUUID().toString();
      final ZonedDateTime createdNow = ZonedDateTime.now();
      tracks.add(
          Track.builder()
              .pkId(UUID.randomUUID())
              .id(id)
              .urn(urn)
              .name(name)
              .durationSec(durationSec)
              .description(description)
              .releasedDate(releasedDate)
              .thumbnail(thumbnail)
              .isPublic(RANDOM.nextBoolean())
              .isPlayable(fileM3u8 != null)
              .tags(name)
              .refCode(refCode)
              .fileM3u8(fileM3u8)
              .totalLikes(RANDOM.nextLong(Integer.MAX_VALUE))
              .totalViews(RANDOM.nextLong(Integer.MAX_VALUE))
              .totalListens(RANDOM.nextLong(Integer.MAX_VALUE))
              .artists(artists)
              .createdAt(createdNow)
              .updatedAt(RANDOM.nextBoolean() ? createdNow : ZonedDateTime.now())
              .createdBy(createdBy)
              .updatedBy(createdBy)
              .build());
    }
    return tracks;
  }
}
