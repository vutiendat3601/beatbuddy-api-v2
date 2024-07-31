package vn.io.vutiendat3601.beatbuddy.config;

import java.time.ZonedDateTime;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import vn.io.vutiendat3601.beatbuddy.security.UserContext;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
public class JpaAuditConfig {
  @Bean
  AuditorAware<String> auditorAware() {
    return () -> Optional.ofNullable(UserContext.getUserId());
  }

  @Bean
  DateTimeProvider dateTimeProvider() {
    return () -> Optional.of(ZonedDateTime.now());
  }
}
