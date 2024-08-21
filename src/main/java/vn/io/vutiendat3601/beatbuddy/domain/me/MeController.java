package vn.io.vutiendat3601.beatbuddy.domain.me;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.beatbuddy.domain.like.LikeDto;
import vn.io.vutiendat3601.beatbuddy.domain.playlist.PlaylistDto;

@SecurityRequirement(name = "web")
@Tag(name = "Me")
@RequiredArgsConstructor
@RequestMapping("v2/me")
@RestController
public class MeController {
  private final MeService meService;

  @GetMapping("like")
  public ResponseEntity<LikeDto> getLike() {
    final LikeDto likeDto = meService.getLike();
    return ResponseEntity.ok(likeDto);
  }

  @GetMapping("playlists")
  public ResponseEntity<List<PlaylistDto>> getMyPlaylist() {
    final List<PlaylistDto> playlistDtos = meService.getAllPlaylist();
    return ResponseEntity.ok(playlistDtos);
  }
}
