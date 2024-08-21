package vn.io.vutiendat3601.beatbuddy.util;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JwtUtils {
  public static String getSub() {
    final Jwt jwt = getJwt();
    if (jwt != null) {
      return jwt.getSubject();
    }
    return null;
  }

  public static Jwt getJwt() {
    final SecurityContext context = SecurityContextHolder.getContext();
    Object authObj = context.getAuthentication();
    if (authObj != null && authObj instanceof JwtAuthenticationToken) {
      return ((JwtAuthenticationToken) authObj).getToken();
    }
    return null;
  }
}
