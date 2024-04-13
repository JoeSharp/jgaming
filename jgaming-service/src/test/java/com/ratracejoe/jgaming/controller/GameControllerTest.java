package com.ratracejoe.jgaming.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.jgaming.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

class GameControllerTest extends AbstractTest {
  @Autowired private TestRestTemplate restTemplate;

  @Test
  void helloWorks() {
    var response =
        restTemplate.exchange(
            "/game/hello", HttpMethod.GET, new HttpEntity<>(null, new HttpHeaders()), String.class);

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
  }
}
