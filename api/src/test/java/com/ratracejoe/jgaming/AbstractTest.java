package com.ratracejoe.jgaming;

import com.redis.testcontainers.RedisContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = JgamingServiceApplication.class)
public abstract class AbstractTest {
  private static final int REDIS_EXPOSED_PORT = 6379;

  private static final RedisContainer REDIS_CONTAINER =
      new RedisContainer(DockerImageName.parse("redis:7.2.4-alpine"))
          .withExposedPorts(REDIS_EXPOSED_PORT);

  static {
    REDIS_CONTAINER.start();
  }

  @Autowired protected TestRestTemplate restTemplate;

  @DynamicPropertySource
  static void dynamicProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
    registry.add(
        "spring.data.redis.port",
        () -> REDIS_CONTAINER.getMappedPort(REDIS_EXPOSED_PORT).toString());
  }
}
