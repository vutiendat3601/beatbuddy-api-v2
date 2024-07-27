package vn.io.vutiendat3601.beatbuddy;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractTestcontainersTest {
  @Container protected static final PostgreSQLContainer<?> POSTGRES_CONTAINER;

  static {
    POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:16.1");
    POSTGRES_CONTAINER.withDatabaseName("fullstack-dao-unit-test");
    POSTGRES_CONTAINER.withUsername("fullstack");
    POSTGRES_CONTAINER.withPassword("fullstack");
  }

  @DynamicPropertySource
  private static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
    registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
  }

  @BeforeAll
  static void migrationDatabaseWithFlyway() {
    final Flyway flyway =
        Flyway.configure()
            .dataSource(
                POSTGRES_CONTAINER.getJdbcUrl(),
                POSTGRES_CONTAINER.getUsername(),
                POSTGRES_CONTAINER.getPassword())
            .load();
    flyway.migrate();
  }

  /* Use this method to get DataSource in case JDBCTemplate is used
  static DataSource getDataSource() {
    return DataSourceBuilder.create()
        .url(POSTGRES_CONTAINER.getJdbcUrl())
        .username(POSTGRES_CONTAINER.getUsername())
        .password(POSTGRES_CONTAINER.getPassword())
        .build();
  }
  */
}
