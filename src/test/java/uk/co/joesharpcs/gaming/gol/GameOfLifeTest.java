package uk.co.joesharpcs.gaming.gol;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameOfLifeTest {
    @Test
    void initialisesCorrectly() {
        // Given
        String boardString = """
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
     * @param boardString The state of board before the step
     */
    @ParameterizedTest
    @ValueSource(strings = {"""
                0..
                .0.
                ...
                ""","""
                ...
                .0.
                ...
                """})
    public void solitude(String boardString) {
        // When
        GameOfLife gameOfLife = GameOfLife.fromString(boardString);
        gameOfLife.iterate();

        // Then
        assertFalse(gameOfLife.isAlive(1,1));
    }

    /**
     * Each cell with four or more neighbours dies, as if by overpopulation.
     * @param boardString The state of board before the step
     */
    @ParameterizedTest
    @ValueSource(strings = {"""
                000
                .0.
                ..0
                ""","""
                ..0
                .00
                00.
                ""","""
                0.0
                .0.
                0.0
                """})
    public void overpopulation(String boardString) {
        // When
        GameOfLife gameOfLife = GameOfLife.fromString(boardString);
        gameOfLife.iterate();

        // Then
        assertFalse(gameOfLife.isAlive(1,1));
    }

    /**
     * Each cell with two or three neighbours survives.
     * @param boardString The state of board before the step
     */
    @ParameterizedTest
    @ValueSource(strings = {"""
                0..
                .0.
                ..0
                ""","""
                ..0
                .0.
                00.
                ""","""
                0..
                .0.
                0.0
                ""","""
                ...
                00.
                ..0
                """})
    public void survives(String boardString) {
        // When
        GameOfLife gameOfLife = GameOfLife.fromString(boardString);
        gameOfLife.iterate();

        // Then
        assertTrue(gameOfLife.isAlive(1,1));
    }

    /**
     * Each cell with three neighbours becomes populated.
     * @param boardString The state of board before the step
     */
    @ParameterizedTest
    @ValueSource(strings = {"""
                0.0
                ...
                ..0
                ""","""
                ...
                ..0
                0.0
                ""","""
                ...
                0..
                00.
                """})
    public void birth(String boardString) {
        // When
        GameOfLife gameOfLife = GameOfLife.fromString(boardString);
        gameOfLife.iterate();

        // Then
        assertTrue(gameOfLife.isAlive(1,1));
    }
}
