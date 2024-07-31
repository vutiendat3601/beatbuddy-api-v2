package vn.io.vutiendat3601.beatbuddy.security;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RouteSpecification {
  private Set<String> uris = new HashSet<>();

  private HttpMethod method;

  private Set<String> authorities = new HashSet<>();
}
