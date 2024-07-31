package vn.io.vutiendat3601.beatbuddy.domain.artist;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "web")
@Tag(name = "Artist")
@RequiredArgsConstructor
@RestController
@RequestMapping("v2/artists")
public class ArtistController {
  private final ArtistService artistService;

  @GetMapping
  public ResponseEntity<List<ArtistDto>> getTrackByIds(@RequestParam List<String> ids) {
    return ResponseEntity.ok(artistService.getArtistByIds(ids));
  }

  @GetMapping("popularity")
  public ResponseEntity<ArtistPopularityDto> getPopularArtist(
      @RequestParam(defaultValue = "10", required = false)
          @Range(min = 5, max = 100, message = "top must be in range [5, 100]")
          Integer top) {
    return ResponseEntity.ok(artistService.getPopularArtist(top));
  }

  @GetMapping("{id}")
  public ResponseEntity<ArtistDetailsDto> getTrackById(@PathVariable String id) {
    return ResponseEntity.ok(artistService.getArtistById(id));
  }

  @GetMapping("{id}/popularity")
  public ResponseEntity<ArtistContentPopularityDto> getArtistPopularContent(
      @PathVariable String id,
      @RequestParam(defaultValue = "10", required = false)
          @Range(min = 5, max = 100, message = "top must be in range [5, 100]")
          Integer top) {
    return ResponseEntity.ok(artistService.getArtistPopularContent(id, top));
  }
}
