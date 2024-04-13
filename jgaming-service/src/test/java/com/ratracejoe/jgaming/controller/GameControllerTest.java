package com.ratracejoe.jgaming.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.jgaming.JgamingServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = JgamingServiceApplication.class)
class GameControllerTest {
  @Autowired private TestRestTemplate restTemplate;

  @Test
  void helloWorks() {
    var response =
        restTemplate.exchange(
            "/game/hello", HttpMethod.GET, new HttpEntity<>(null, new HttpHeaders()), String.class);

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
  }
}
