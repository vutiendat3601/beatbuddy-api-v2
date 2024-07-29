package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistConstant.PLAYLIST_URN_PREFIX;
import static vn.io.vutiendat3601.beatbuddy.util.TrackFakerUtils.randomTrack;

import com.github.javafaker.Faker;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.io.vutiendat3601.beatbuddy.domain.artist.ArtistDtoMapper;
import vn.io.vutiendat3601.beatbuddy.domain.track.Track;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDao;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDto;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDtoMapper;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTest {
  private final Faker FAKER = new Faker();
  private final TrackDtoMapper trackDtoMapper = new TrackDtoMapper(new ArtistDtoMapper());
  @Mock private PlaylistDao playlistDao;

  @Mock private TrackDao trackDao;
  private final PlaylistDtoMapper playlistDtoMapper = new PlaylistDtoMapper();
  private PlaylistService underTest;

  @BeforeEach
  void setUp() {
    underTest = new PlaylistService(playlistDao, trackDao, playlistDtoMapper, trackDtoMapper);
  }

  @Test
  void testCreatePlaylist() {
    // Given
    final String name = FAKER.funnyName().name();
    final String description = FAKER.lorem().sentence();
    final Boolean isPublic = FAKER.bool().bool();
    final Set<String> tags = Set.of(FAKER.lorem().word(), FAKER.lorem().word());

    // When
    underTest.createPlaylist(new PlaylistCreateRequest(name, description, isPublic, tags));

    // Then
    final ArgumentCaptor<Playlist> playlistCaptor = ArgumentCaptor.forClass(Playlist.class);
    verify(playlistDao).insert(playlistCaptor.capture());
    final Playlist playlist = playlistCaptor.getValue();
    assertEquals(name, playlist.getName());
    assertEquals(description, playlist.getDescription());
    assertEquals(isPublic, playlist.getIsPublic());
    assertEquals(tags, playlist.getTags());
  }

  @Test
  void testGetAllPlaylistByCurrentUser() {
    // TODO: implement this test
  }

  @Test
  void testGetPlaylistById() {
    // Given
    final String id = FAKER.random().hex(16);
    final String urn = PLAYLIST_URN_PREFIX + id;
    final String name = FAKER.funnyName().name();
    final String thumbnail = FAKER.internet().url();
    final String description = FAKER.lorem().sentence();
    final Boolean isPublic = FAKER.bool().bool();
    final String ownerId = "1a6896a1-225f-4bc2-a7bb-17db09b7dfa4";
    final Set<String> tags = Set.of(FAKER.lorem().word(), FAKER.lorem().word());
    final List<Track> tracks = List.of(randomTrack(), randomTrack());
    final List<String> itemUrns = tracks.stream().map(Track::getUrn).toList();
    final List<String> trackUrns = tracks.stream().map(Track::getUrn).toList();
    final ZonedDateTime createdAt = ZonedDateTime.now();
    final ZonedDateTime updatedAt = ZonedDateTime.now();
    final List<TrackDto> trackDtos = tracks.stream().map(trackDtoMapper::apply).toList();
    final Playlist playlist =
        Playlist.builder()
            .id(id)
            .urn(urn)
            .name(name)
            .thumbnail(thumbnail)
            .description(description)
            .isPublic(isPublic)
            .itemUrns(itemUrns)
            .ownerId(ownerId)
            .tags(tags)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();
    when(trackDao.selectByUrns(trackUrns)).thenReturn(tracks);
    when(playlistDao.selectById(id)).thenReturn(Optional.of(playlist));

    // When
    final PlaylistDetailsDto actual = underTest.getPlaylistDetailsById(id);

    // Then
    assertEquals(id, actual.id());
    assertEquals(urn, actual.urn());
    assertEquals(name, actual.name());
    assertEquals(thumbnail, actual.thumbnail());
    assertEquals(description, actual.description());
    assertEquals(isPublic, actual.isPublic());
    assertEquals(ownerId, actual.ownerId());
    assertEquals(tags, actual.tags());
    assertEquals(trackDtos, actual.tracks());
    assertEquals(createdAt, actual.createdAt());
    assertEquals(updatedAt, actual.updatedAt());
  }
}
