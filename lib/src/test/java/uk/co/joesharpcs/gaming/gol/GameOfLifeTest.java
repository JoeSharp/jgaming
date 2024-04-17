package uk.co.joesharpcs.gaming.gol;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GameOfLifeTest {
  @Test
  void initialisesCorrectly() {
    // Given
    String boardString =
        """
                ..........
                ..........
                ..........
                ..0.......
                ....0.....
                0.........
                ..........
                ..........
                ..........
                ..........
                """;

    // When
    GameOfLife gameOfLife = GameOfLife.fromString(boardString);

    // Then
    assertFalse(gameOfLife.isAlive(3, 3));
    assertTrue(gameOfLife.isAlive(3, 2));
    assertTrue(gameOfLife.isAlive(5, 0));
  }

  /**
   * Each cell with one or no neighbours dies, as if by solitude.
   *
   * @param boardString The state of board before the step
   */
  @ParameterizedTest
  @ValueSource(
      strings = {
        """
                0..
                .0.
                ...
                """,
        """
                ...
                .0.
                ...
                """
      })
  void solitude(String boardString) {
    // When
    GameOfLife gameOfLife = GameOfLife.fromString(boardString);
    gameOfLife.iterate();

    // Then
    assertFalse(gameOfLife.isAlive(1, 1));
  }

  /**
   * Each cell with four or more neighbours dies, as if by overpopulation.
   *
   * @param boardString The state of board before the step
   */
  @ParameterizedTest
  @ValueSource(
      strings = {
        """
                000
                .0.
                ..0
                """,
        """
                ..0
                .00
                00.
                """,
        """
                0.0
                .0.
                0.0
                """
      })
  void overpopulation(String boardString) {
    // When
    GameOfLife gameOfLife = GameOfLife.fromString(boardString);
    gameOfLife.iterate();

    // Then
    assertFalse(gameOfLife.isAlive(1, 1));
  }

  /**
   * Each cell with two or three neighbours survives.
   *
   * @param boardString The state of board before the step
   */
  @ParameterizedTest
  @ValueSource(
      strings = {
        """
                0..
                .0.
                ..0
                """,
        """
                ..0
                .0.
                00.
                """,
        """
                0..
                .0.
                0.0
                """,
        """
                ...
                00.
                ..0
                """
      })
  void survives(String boardString) {
    // When
    GameOfLife gameOfLife = GameOfLife.fromString(boardString);
    gameOfLife.iterate();

    // Then
    assertTrue(gameOfLife.isAlive(1, 1));
  }

  /**
   * Each cell with three neighbours becomes populated.
   *
   * @param boardString The state of board before the step
   */
  @ParameterizedTest
  @ValueSource(
      strings = {
        """
                0.0
                ...
                ..0
                """,
        """
                ...
                ..0
                0.0
                """,
        """
                ...
                0..
                00.
                """
      })
  void birth(String boardString) {
    // When
    GameOfLife gameOfLife = GameOfLife.fromString(boardString);
    gameOfLife.iterate();

    // Then
    assertTrue(gameOfLife.isAlive(1, 1));
  }

  @ParameterizedTest
  @ValueSource(strings = {GolPatterns.BEACON, GolPatterns.BLINKER})
  void oscillators(String pattern) {
    // Given
    GameOfLife gameOfLife = GameOfLife.fromString(pattern);

    // When
    for (int i = 0; i < 10; i++) {
      gameOfLife.iterate();
    }

    // Then
    assertEquals(pattern.trim(), gameOfLife.toString().trim());
  }
}
