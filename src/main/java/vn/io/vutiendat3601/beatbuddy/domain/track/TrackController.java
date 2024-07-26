package vn.io.vutiendat3601.beatbuddy.domain.track;

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
@RequestMapping("v1/tracks")
public class TrackController {
  private final TrackService trackService;

  @GetMapping("{id}")
  public ResponseEntity<TrackDto> getTrackById(@PathVariable String id) {
    return ResponseEntity.ok(trackService.getTrackById(id));
  }

  @GetMapping
  public ResponseEntity<List<TrackDto>> getTrackByIds(@RequestParam List<String> ids) {
    return ResponseEntity.ok(trackService.getTrackByIds(ids));
  }
}
