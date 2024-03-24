package uk.co.joesharpcs.gaming.board;

import java.util.function.BiConsumer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BoardLocationTest {
  @Test
  public void fromTopLeft() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacent(4, 0, 0, receiver);

    // Then
    Mockito.verify(receiver).accept(1, 0);
    Mockito.verify(receiver).accept(0, 1);
  }

  @Test
  public void fromTopLeftWithDiagonals() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacentWithDiagonals(4, 0, 0, receiver);

    // Then
    Mockito.verify(receiver).accept(1, 0);
    Mockito.verify(receiver).accept(0, 1);
    Mockito.verify(receiver).accept(1, 1);
  }

  @Test
  public void fromTopRight() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacent(4, 0, 3, receiver);

    // Then
    Mockito.verify(receiver).accept(1, 3);
    Mockito.verify(receiver).accept(0, 2);
  }

  @Test
  public void fromTopRightWithDiagonals() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacentWithDiagonals(4, 0, 3, receiver);

    // Then
    Mockito.verify(receiver).accept(1, 3);
    Mockito.verify(receiver).accept(0, 2);
    Mockito.verify(receiver).accept(1, 2);
  }

  @Test
  public void fromTopRow() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacent(4, 0, 1, receiver);

    // Then
    Mockito.verify(receiver).accept(1, 1);
    Mockito.verify(receiver).accept(0, 0);
    Mockito.verify(receiver).accept(0, 2);
  }

  @Test
  public void fromTopRowWithDiagonals() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacentWithDiagonals(4, 0, 1, receiver);

    // Then
    Mockito.verify(receiver).accept(1, 1);
    Mockito.verify(receiver).accept(0, 0);
    Mockito.verify(receiver).accept(0, 2);
    Mockito.verify(receiver).accept(1, 1);
    Mockito.verify(receiver).accept(1, 2);
  }

  @Test
  public void fromBottomLeft() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacent(4, 3, 0, receiver);

    // Then
    Mockito.verify(receiver).accept(2, 0);
    Mockito.verify(receiver).accept(3, 1);
  }

  @Test
  public void fromBottomLeftWithDiagonals() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacentWithDiagonals(4, 3, 0, receiver);

    // Then
    Mockito.verify(receiver).accept(2, 0);
    Mockito.verify(receiver).accept(3, 1);
    Mockito.verify(receiver).accept(2, 1);
  }

  @Test
  public void fromBottomRight() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacent(4, 3, 3, receiver);

    // Then
    Mockito.verify(receiver).accept(2, 3);
    Mockito.verify(receiver).accept(3, 2);
  }

  @Test
  public void fromBottomRightWithDiagonals() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacentWithDiagonals(4, 3, 3, receiver);

    // Then
    Mockito.verify(receiver).accept(2, 3);
    Mockito.verify(receiver).accept(3, 2);
    Mockito.verify(receiver).accept(2, 2);
  }

  @Test
  public void fromBottomRow() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacent(4, 3, 1, receiver);

    // Then
    Mockito.verify(receiver).accept(3, 0);
    Mockito.verify(receiver).accept(3, 2);
    Mockito.verify(receiver).accept(2, 1);
  }

  @Test
  public void fromBottomRowWithDiagonals() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacentWithDiagonals(4, 3, 1, receiver);

    // Then
    Mockito.verify(receiver).accept(3, 0);
    Mockito.verify(receiver).accept(3, 2);
    Mockito.verify(receiver).accept(2, 1);
    Mockito.verify(receiver).accept(2, 2);
    Mockito.verify(receiver).accept(2, 0);
  }

  @Test
  public void fromLeftEdge() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacent(4, 2, 0, receiver);

    // Then
    Mockito.verify(receiver).accept(2, 1);
    Mockito.verify(receiver).accept(1, 0);
    Mockito.verify(receiver).accept(3, 0);
  }

  @Test
  public void fromLeftEdgeWithDiagonals() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacentWithDiagonals(4, 2, 0, receiver);

    // Then
    Mockito.verify(receiver).accept(2, 1);
    Mockito.verify(receiver).accept(1, 0);
    Mockito.verify(receiver).accept(3, 0);
    Mockito.verify(receiver).accept(1, 1);
    Mockito.verify(receiver).accept(3, 1);
  }

  @Test
  public void fromRightEdge() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacent(4, 2, 3, receiver);

    // Then
    Mockito.verify(receiver).accept(2, 2);
    Mockito.verify(receiver).accept(1, 3);
    Mockito.verify(receiver).accept(3, 3);
  }

  @Test
  public void fromRightEdgeWithDiagonals() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacentWithDiagonals(4, 2, 3, receiver);

    // Then
    Mockito.verify(receiver).accept(2, 2);
    Mockito.verify(receiver).accept(1, 3);
    Mockito.verify(receiver).accept(3, 3);
    Mockito.verify(receiver).accept(1, 2);
    Mockito.verify(receiver).accept(3, 2);
  }

  @Test
  public void fromMiddle() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacent(4, 2, 2, receiver);

    // Then
    Mockito.verify(receiver).accept(2, 1);
    Mockito.verify(receiver).accept(2, 3);
    Mockito.verify(receiver).accept(1, 2);
    Mockito.verify(receiver).accept(3, 2);
  }

  @Test
  public void fromMiddleWithDiagonals() {
    // Given
    BiConsumer<Integer, Integer> receiver = Mockito.mock();

    // When
    BoardLocation.getAdjacentWithDiagonals(4, 2, 2, receiver);

    // Then
    Mockito.verify(receiver).accept(2, 1);
    Mockito.verify(receiver).accept(2, 3);
    Mockito.verify(receiver).accept(1, 2);
    Mockito.verify(receiver).accept(3, 2);
    Mockito.verify(receiver).accept(1, 1);
    Mockito.verify(receiver).accept(1, 3);
    Mockito.verify(receiver).accept(3, 1);
    Mockito.verify(receiver).accept(3, 3);
  }
}
