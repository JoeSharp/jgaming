package uk.co.joesharpcs.gaming.go;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import uk.co.joesharpcs.gaming.go.exceptions.*;

import static org.junit.jupiter.api.Assertions.*;

class GoBoardTest {
    @Test
    public void defaultBoardCreated() {
        // When
        var board = new GoBoard();

        // Then
        assertEquals(19, board.getSize());
        assertEquals(Player.BLACK, board.getWhosTurn());
    }

    @Test
    public void specifiedSmallBoardCreated() throws GoException {
        // When
        final int CUSTOM_BOARD_SIZE = 9;
        var board = new GoBoard(CUSTOM_BOARD_SIZE);

        // Then
        assertEquals(CUSTOM_BOARD_SIZE, board.getSize());
        assertEquals(Player.BLACK, board.getWhosTurn());

        // Board is empty
        for (int row=0; row < CUSTOM_BOARD_SIZE; row++) {
            for(int col=0; col < CUSTOM_BOARD_SIZE; col++) {
                var value = board.getValue(row, col);
                assertEquals(PointValue.EMPTY, value);
            }
        }
    }

    @Test
    public void wrongPlayerTriesToStart() {
        // Given
        var board = new GoBoard();

        // When
        assertThrows(WrongPlayerException.class, () -> {
           board.move(Player.WHITE, 1, 2);
        });
    }

    @Test
    public void firstMovesCorrect() throws GoException {
        // Given
        var board = new GoBoard();

        // When
        board.move(Player.BLACK, 4, 5);
        var result = board.getValue(4, 5);

        // Then
        assertEquals(PointValue.BLACK, result);
    }

    @Test
    public void getInvalidLocation() {
        // Given
        var board = new GoBoard(4);

        // When, Then
        assertThrows(InvalidPointLocationException.class, () -> board.getValue(4, 1));
        assertThrows(InvalidPointLocationException.class, () -> board.getValue(1, 4));
    }

    @Test
    public void moveInvalidLocation() {
        // Given
        var board = new GoBoard(5);

        // When, Then
        assertThrows(InvalidPointLocationException.class, () -> board.move(Player.BLACK, 5, 1));
        assertThrows(InvalidPointLocationException.class, () -> board.move(Player.BLACK, 1, 5));
    }

    @Test
    public void secondMovesCorrect() throws GoException {
        // Given
        var board = new GoBoard();

        // When
        board.move(Player.BLACK, 4, 5);
        board.move(Player.WHITE, 4, 6);
        var result = board.getValue(4, 6);

        // Then
        assertEquals(PointValue.WHITE, result);
    }

    @Test
    public void alreadyOccupiedThrownCorrect() throws GoException {
        // Given
        var board = new GoBoard();

        // When
        board.move(Player.BLACK, 4, 7);

        // Then
        assertThrows(AlreadyOccupiedException.class, () -> board.move(Player.WHITE, 4, 7));
    }

    @Test
    public void initialisedByString() throws GoException {
        // Given
        var board = new GoBoard(9, """
                .........
                .........
                ....BBB..
                ...BW.B..
                ....WWB..
                ....BB...
                .........
                .........
                .........
                """);
        
        // When
        var value24 = board.getValue(2, 4);
        var value34 = board.getValue(3, 4);
        var value53 = board.getValue(5, 3);

        // Then
        assertEquals(PointValue.BLACK, value24);
        assertEquals(PointValue.WHITE, value34);
        assertEquals(PointValue.EMPTY, value53);
    }

    @ParameterizedTest
    @ValueSource(strings = {"""
                .........
                .........
                ....BBB..
                ...BX.B..
                ....WWB..
                ....BB...
                .........
                .........
                .........
                """, """
                .........
                ....BBB..
                ...BW.B..
                ....WWB..
                ....BB...
                .........
                .........
                .........
                ""","""
                ........
                ........
                ....BBB.
                ...BW.B.
                ....WWB.
                ....BB..
                ........
                ........
                ........
                """})
    public void initialisedByInvalidString(String value) throws GoException {
        // Given
        assertThrows(InvalidBoardStateStringException.class, () -> new GoBoard(9, value));
    }
}