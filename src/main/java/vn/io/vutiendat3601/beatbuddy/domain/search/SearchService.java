package vn.io.vutiendat3601.beatbuddy.domain.search;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.beatbuddy.common.model.Page;
import vn.io.vutiendat3601.beatbuddy.domain.artist.Artist;
import vn.io.vutiendat3601.beatbuddy.domain.artist.ArtistDao;
import vn.io.vutiendat3601.beatbuddy.domain.artist.ArtistDto;
import vn.io.vutiendat3601.beatbuddy.domain.artist.ArtistDtoMapper;
import vn.io.vutiendat3601.beatbuddy.domain.playlist.Playlist;
import vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistDao;
import vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistDto;
import vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistDtoMapper;
import vn.io.vutiendat3601.beatbuddy.domain.track.Track;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDao;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDto;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDtoMapper;
import vn.io.vutiendat3601.beatbuddy.util.StringUtils;

@RequiredArgsConstructor
@Service
public class SearchService {
  private final TrackDao trackDao;
  private final ArtistDao artistDao;
  private final PlaylistDao playlistDao;
  private final TrackDtoMapper trackDtoMapper;
  private final ArtistDtoMapper artistDtoMapper;
  private final PlaylistDtoMapper playlistDtoMapper;

  public SearchDto search(@NonNull SearchRequest searchReq) {
    final String keyword = StringUtils.removeAccent(searchReq.keyword());
    final int page = searchReq.page() - 1;
    final int size = searchReq.size();
    final Set<SearchType> types = searchReq.types();

    Page<TrackDto> track = null;
    Page<ArtistDto> artist = null;
    Page<PlaylistDto> playlist = null;
    for (SearchType type : types) {
      switch (type) {
        case TRACK:
          final Page<Track> trackPage = trackDao.selectByKeyword(keyword, page, size);
          track = trackPage.map(trackDtoMapper::apply);
          break;
        case ARTIST:
          final Page<Artist> artistPage = artistDao.selectByKeyword(keyword, page, size);
          artist = artistPage.map(artistDtoMapper::apply);
          break;
        case PLAYLIST:
          final Page<Playlist> playlistPage = playlistDao.selectByKeyword(keyword, page, size);
          playlist = playlistPage.map(playlistDtoMapper::apply);
          break;
        default:
          throw new IllegalArgumentException("Unknown search type: " + type);
      }
    }
    return new SearchDto(track, artist, playlist, keyword, types, searchReq.page(), size);
  }
}
