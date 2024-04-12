package uk.co.joesharpcs.gaming.go;

import java.util.Arrays;
import java.util.Objects;
import uk.co.joesharpcs.gaming.go.exceptions.InvalidPointValueException;

public enum PointValue {
  WHITE("W"),
  BLACK("B"),
  EMPTY(".");

  final String asChar;

  PointValue(String asChar) {
    this.asChar = asChar;
  }

  public String toString() {
    return this.asChar;
  }

  static PointValue fromChar(String c) {
    return Arrays.stream(PointValue.values())
        .filter(v -> Objects.equals(v.asChar, c))
        .findAny()
        .orElseThrow(InvalidPointValueException::new);
  }
}
