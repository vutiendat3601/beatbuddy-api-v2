package vn.io.vutiendat3601.beatbuddy.domain.artist;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.beatbuddy.domain.track.TrackDto;

@Tag(name = "Artist")
@RequiredArgsConstructor
@RestController
@RequestMapping("v2/artists")
public class ArtistController {
  private final ArtistService artistService;

  @GetMapping("{id}")
  public ResponseEntity<ArtistDetailsDto> getTrackById(@PathVariable String id) {
    return ResponseEntity.ok(artistService.getArtistById(id));
  }

  @GetMapping
  public ResponseEntity<List<ArtistDto>> getTrackByIds(@RequestParam List<String> ids) {
    return ResponseEntity.ok(artistService.getArtistByIds(ids));
  }

  @GetMapping("{id}/popularity")
  public ResponseEntity<List<TrackDto>> getArtistPopularTrack(
      @PathVariable String id, @RequestParam(defaultValue = "10", required = false) Integer top) {
    return ResponseEntity.ok(artistService.getPopularTrack(id, top));
  }
}
