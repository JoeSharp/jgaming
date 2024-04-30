package com.ratracejoe.jgaming.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.jgaming.AbstractTest;
import com.ratracejoe.jgaming.model.IdentifiedGameOfLife;
import com.ratracejoe.jgaming.redis.GameOfLifeRepository;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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

  @Test
  void templateListWorks() {
    // When
    var response =
        restTemplate.exchange(
            "/gol/template/names",
            HttpMethod.GET,
            new HttpEntity<>(null, new HttpHeaders()),
            new ParameterizedTypeReference<Set<String>>() {});

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull().contains("beacon", "boat");
  }
}
