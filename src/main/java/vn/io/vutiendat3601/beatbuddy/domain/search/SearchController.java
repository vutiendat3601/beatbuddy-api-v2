package vn.io.vutiendat3601.beatbuddy.domain.search;

import static vn.io.vutiendat3601.beatbuddy.domain.search.SearchConstant.SEARCH_WS_BROKER_V2;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SearchController {
  private final SearchService searchService;

  @MessageMapping("/ws/v2/search")
  @SendToUser(value = SEARCH_WS_BROKER_V2 + "/result", broadcast = true)
  public SearchDto search(@Payload SearchRequest searchReq) {
    return searchService.search(searchReq);
  }
}
