package vn.io.vutiendat3601.beatbuddy.domain.artist;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/artists")
public class ArtistController {
  private final ArtistService artistService;

  @GetMapping("{id}")
  public ResponseEntity<ArtistDto> getTrackById(@PathVariable String id) {
    return ResponseEntity.ok(artistService.getArtistById(id));
  }

  @GetMapping
  public ResponseEntity<List<ArtistDto>> getTrackByIds(@RequestParam List<String> ids) {
    return ResponseEntity.ok(artistService.getArtistByIds(ids));
  }
}
