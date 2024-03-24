package uk.co.joesharpcs.gaming.go;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import uk.co.joesharpcs.gaming.go.exceptions.InvalidPointValueException;

class PointValueTest {
  @Test
  public void whiteCorrect() throws InvalidPointValueException {
    var result = PointValue.fromChar("W");
    assertEquals(PointValue.WHITE, result);
  }

  @Test
  public void blackCorrect() throws InvalidPointValueException {
    var result = PointValue.fromChar("B");
    assertEquals(PointValue.BLACK, result);
  }

  @Test
  public void emptyCorrect() throws InvalidPointValueException {
    var result = PointValue.fromChar(".");
    assertEquals(PointValue.EMPTY, result);
  }
}
