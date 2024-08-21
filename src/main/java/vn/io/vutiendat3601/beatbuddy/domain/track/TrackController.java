package vn.io.vutiendat3601.beatbuddy.domain.track;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "web")
@Tag(name = "Track")
@RequiredArgsConstructor
@RestController
@RequestMapping("v2/tracks")
public class TrackController {
  private final TrackService trackService;

  @GetMapping("{id}")
  public ResponseEntity<TrackDetailsDto> getTrackById(@PathVariable String id) {
    return ResponseEntity.ok(trackService.getTrackDetailsById(id));
  }

  @GetMapping
  public ResponseEntity<List<TrackDto>> getTrackByIds(@RequestParam List<String> ids) {
    return ResponseEntity.ok(trackService.getTrackByIds(ids));
  }

  @GetMapping("popularity")
  public ResponseEntity<TrackPopularityDto> getPopularTrack(
      @RequestParam(defaultValue = "10", required = false)
          @Range(min = 5, max = 100, message = "top must be in range [5, 100]")
          Integer top) {
    return ResponseEntity.ok(trackService.getPopularTrack(top));
  }

  @MessageMapping("/ws/v2/tracks/like")
  public void likeTrack(@Payload TrackLikeRequest trackLikeReq) {
    trackService.likeTrack(trackLikeReq);
  }
}
