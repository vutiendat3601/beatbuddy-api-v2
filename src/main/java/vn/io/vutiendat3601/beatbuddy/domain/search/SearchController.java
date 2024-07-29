package vn.io.vutiendat3601.beatbuddy.domain.search;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SearchController {
  private final SearchService searchService;

  @MessageMapping("/search")
  @SendTo("/ws/v2/search/result")
  public SearchDto search(@Payload SearchRequest searchReq) {
    return searchService.search(searchReq);
  }
}
