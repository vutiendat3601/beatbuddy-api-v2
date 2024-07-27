package vn.io.vutiendat3601.beatbuddy;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class TestcontainersTest extends AbstractTestcontainersTest {
  @Test
  void canStartPostgresContainer() {
    assertTrue(POSTGRES_CONTAINER.isCreated());
    assertTrue(POSTGRES_CONTAINER.isRunning());
  }
}
