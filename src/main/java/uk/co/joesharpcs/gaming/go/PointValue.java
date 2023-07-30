package uk.co.joesharpcs.gaming.go;

import uk.co.joesharpcs.gaming.go.exceptions.InvalidPointValueException;

import java.util.Arrays;
import java.util.Objects;

public enum PointValue {
    WHITE("W"),
    BLACK("B"),
    EMPTY(".");

    final String asChar;

    PointValue(String asChar) {
        this.asChar = asChar;
    }

    String toChar() {
        return this.asChar;
    }

    static PointValue fromChar(String c) throws InvalidPointValueException {
        return Arrays.stream(PointValue.values())
                .filter(v -> Objects.equals(v.asChar, c)).findAny()
                .orElseThrow(InvalidPointValueException::new);
    }
}
