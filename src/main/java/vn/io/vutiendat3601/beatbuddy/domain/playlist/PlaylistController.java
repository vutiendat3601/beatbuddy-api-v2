package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.beatbuddy.common.dto.ResponseDto;

@Tag(name = "Playlist")
@RequiredArgsConstructor
@RestController
@RequestMapping("v2/playlists")
public class PlaylistController {
  private final PlaylistService playlistService;

  @PostMapping
  public ResponseEntity<ResponseDto> createPlaylist(
      @RequestBody PlaylistCreateRequest playlistCreateReq) {
    playlistService.createPlaylist(playlistCreateReq);
    return ResponseEntity.ok(
        new ResponseDto(
            "Playlist created successfully",
            HttpStatus.OK,
            HttpStatus.OK.value(),
            ZonedDateTime.now()));
  }

  @GetMapping("{id}")
  public ResponseEntity<PlaylistDetailsDto> getPlaylistById(@PathVariable String id) {
    final PlaylistDetailsDto playlistDetailsDto = playlistService.getPlaylistDetailsById(id);
    return ResponseEntity.ok(playlistDetailsDto);
  }

  @GetMapping("me")
  public ResponseEntity<List<PlaylistDto>> getMyPlaylist() {
    final List<PlaylistDto> playlistDtos = playlistService.getAllPlaylistByCurrentUser();
    return ResponseEntity.ok(playlistDtos);
  }

  @PutMapping("{id}")
  public ResponseEntity<ResponseDto> updatePlaylistItem(
      @PathVariable String id, @RequestBody PlaylistUpdateRequest playlistItemUpdateReq) {
    playlistService.updatePlaylist(id, playlistItemUpdateReq);
    return ResponseEntity.ok(
        new ResponseDto(
            "Playlist updated successfully",
            HttpStatus.OK,
            HttpStatus.OK.value(),
            ZonedDateTime.now()));
  }
}
