package com.ratracejoe.jgaming.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.jgaming.AbstractTest;
import com.ratracejoe.jgaming.model.IdentifiedGameOfLife;
import com.ratracejoe.jgaming.redis.GameOfLifeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

class GameOfLifeControllerTest extends AbstractTest {

  @Autowired private GameOfLifeRepository repository;

  @Test
  void createWorks() {
    // When
    var response =
        restTemplate.exchange(
            "/gol/create",
            HttpMethod.POST,
            new HttpEntity<>(null, new HttpHeaders()),
            IdentifiedGameOfLife.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isNotNull();
    var created = repository.findById(response.getBody().id());
    assertThat(created).isPresent();
  }
}
