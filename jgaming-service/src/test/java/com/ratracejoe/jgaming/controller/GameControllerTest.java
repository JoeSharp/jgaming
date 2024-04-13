package com.ratracejoe.jgaming.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.jgaming.AbstractTest;
import com.ratracejoe.jgaming.dto.NewGameDTO;
import com.ratracejoe.jgaming.model.GameType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

class GameControllerTest extends AbstractTest {
  @Autowired private TestRestTemplate restTemplate;

  @Test
  void helloWorks() {
    var response =
        restTemplate.exchange(
            "/game/hello", HttpMethod.GET, new HttpEntity<>(null, new HttpHeaders()), String.class);

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
  }

  @Test
  void gameCreated() {
    // Given
    var newGame = new NewGameDTO("testUser123", GameType.GO_FISH, "A test game");
    var headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    headers.set(HttpHeaders.ACCEPT, MediaType.TEXT_PLAIN_VALUE);

    // When
    var createdResponse =
        restTemplate.exchange(
            "/game/start", HttpMethod.POST, new HttpEntity<>(newGame, headers), String.class);

    // Then
    assertThat(createdResponse.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(createdResponse.getBody()).isNotNull();
  }
}
