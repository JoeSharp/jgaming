package com.ratracejoe.jgaming.service.gol;

import static org.assertj.core.api.Assertions.*;

import com.ratracejoe.jgaming.AbstractTest;
import com.ratracejoe.jgaming.exception.GameNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class GameOfLifeServiceTest extends AbstractTest {
  @Autowired private GameOfLifeService service;

  @Test
  void getAndIterateWork() throws GameNotFoundException {
    var initialGame = service.create();

    for (int i = 0; i < 10; i++) {
      service.iterate(initialGame.id());
    }

    var result = service.get(initialGame.id());
    // Default game is an oscillator
    assertThat(result).hasToString(initialGame.toString());
  }

  @Test
  void destroyWorks() {
    // Given
    var initialGame = service.create();

    // When
    service.destroy(initialGame.id());

    // Then
    assertThatThrownBy((() -> service.get(initialGame.id())))
        .isInstanceOf(GameNotFoundException.class);
  }
}
