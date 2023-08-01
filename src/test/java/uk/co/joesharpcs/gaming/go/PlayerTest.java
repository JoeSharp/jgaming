package uk.co.joesharpcs.gaming.go;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    @Test
    public void nextPlayerCorrectFromWhite() {
        // Given
        var player = Player.WHITE;

        // When
        var result = player.otherPlayer();

        // Then
        assertEquals(Player.BLACK, result);
    }

    @Test
    public void nextPlayerCorrectFromBlack() {
        // Given
        var player = Player.BLACK;

        // When
        var result = player.otherPlayer();

        // Then
        assertEquals(Player.WHITE, result);
    }

    @Test
    public void pointValues() {
        assertEquals(Player.WHITE.getPointValue(), PointValue.WHITE);
        assertEquals(Player.BLACK.getPointValue(), PointValue.BLACK);
    }
}
