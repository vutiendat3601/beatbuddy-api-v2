package vn.io.vutiendat3601.beatbuddy.security;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@RequiredArgsConstructor
public class TokenAuthenticationInterceptor implements ChannelInterceptor {
  private final JwtDecoder jwtDecoder;

  @Override
  @Nullable
  public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
    final StompHeaderAccessor accessor =
        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
      String token = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
      token = token == null ? accessor.getFirstNativeHeader(SecurityConstant.CLIENT_TOKEN) : token;
      if (token != null && token.startsWith("Bearer ") && token.length() > 7) {
        token = token.substring(7);
        final Jwt jwt = jwtDecoder.decode(token);
        final JwtAuthenticationToken jwtAuthenticationToken =
            new JwtAuthenticationToken(jwt, new ArrayList<>());
        accessor.setUser(jwtAuthenticationToken);
      } else {
        throw new AccessDeniedException("Access Denied Exception");
      }
    }
    return message;
  }
}
