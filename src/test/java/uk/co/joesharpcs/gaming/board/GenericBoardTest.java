package uk.co.joesharpcs.gaming.board;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenericBoardTest {

    private enum MadeUp {
        ONE("1"),
        TWO("2");

        private final String asString;

        MadeUp(String asString) {
            this.asString = asString;
        }

        static MadeUp fromString(String str) throws BoardValueException {
            return Arrays.stream(values())
                    .filter(v -> v.asString.equals(str)).findFirst()
                    .orElseThrow(BoardValueException::new);
        }
    }

    @Test
    public void boardLoadsFromString() throws BoardValueException {
        // Given
        String boardStr = """
                1211
                2112
                2221
                1122
                """;

        // When
        var result = GenericBoard.fromString(boardStr, MadeUp::fromString);

        // Then
        assertEquals(MadeUp.ONE, result.get(0,0));
    }
}
