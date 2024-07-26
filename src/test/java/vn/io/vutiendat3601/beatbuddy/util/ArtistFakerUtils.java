package vn.io.vutiendat3601.beatbuddy.util;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import vn.io.vutiendat3601.beatbuddy.domain.artist.Artist;

public class ArtistFakerUtils {
  private static final String[] COUNTRY_CODES = Locale.getISOCountries();
  private static final Faker FAKER = new Faker();
  private static final RandomService RANDOM = FAKER.random();
  private static final String[] REF_CODE_PREFIXES = {"spt_", "ytb_", "zmp3_"};

  public static Map<String, Artist> randomArtistMap(int number) {
    return randomArtistList(number).stream().collect(Collectors.toMap(Artist::getId, t -> t));
  }

  public static Artist randomArtist() {
    return randomArtistList(1).get(0);
  }

  public static List<String> randomArtistIds(int number) {
    return randomArtistList(number).stream().map(Artist::getId).collect(Collectors.toList());
  }

  public static String randomArtistId() {
    return randomArtist().getId();
  }

  public static List<Artist> randomArtistList(int number) {
    final List<Artist> arirts = new ArrayList<>();

    for (int i = 0; i < number; i++) {
      final String id = RANDOM.hex(16).toLowerCase();
      final String urn = "beatbuddy:artist:" + id;
      final String name = FAKER.funnyName().name();
      final String realName = FAKER.name().fullName();
      final String description = RANDOM.nextBoolean() ? FAKER.lorem().sentence() : null;
      final String biography = RANDOM.nextBoolean() ? FAKER.lorem().sentence() : null;
      final String thumbnail =
          RANDOM.nextBoolean()
              ? "https://%s/files/%s.jpg".formatted(FAKER.internet().url(), id)
              : null;
      final String background =
          RANDOM.nextBoolean()
              ? "https://%s/files/%s.jpg".formatted(FAKER.internet().url(), id)
              : null;
      final LocalDate birthDate =
          RANDOM.nextBoolean()
              ? FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
              : null;
      final String nationality =
          RANDOM.nextBoolean() ? COUNTRY_CODES[RANDOM.nextInt(COUNTRY_CODES.length)] : null;
      final String refCode =
          REF_CODE_PREFIXES[RANDOM.nextInt(REF_CODE_PREFIXES.length)]
              + RANDOM.hex(RANDOM.nextInt(10, 32));
      final String createdBy = UUID.randomUUID().toString();
      final ZonedDateTime createdAt = ZonedDateTime.now();
      final ZonedDateTime updatedAt = RANDOM.nextBoolean() ? createdAt : ZonedDateTime.now();

      arirts.add(
          Artist.builder()
              .pkId(UUID.randomUUID())
              .id(id)
              .urn(urn)
              .name(name)
              .isVerified(RANDOM.nextBoolean())
              .isPublic(RANDOM.nextBoolean())
              .realName(realName)
              .birthDate(birthDate)
              .description(description)
              .nationality(nationality)
              .biography(biography)
              .thumbnail(thumbnail)
              .background(background)
              .tags(name)
              .refCode(refCode)
              .totalLikes(RANDOM.nextLong(Integer.MAX_VALUE))
              .totalViews(RANDOM.nextLong(Integer.MAX_VALUE))
              .createdAt(createdAt)
              .updatedAt(updatedAt)
              .createdBy(createdBy)
              .updatedBy(createdBy)
              .build());
    }
    return arirts;
  }
}
