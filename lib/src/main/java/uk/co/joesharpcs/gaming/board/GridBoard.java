package uk.co.joesharpcs.gaming.board;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;
import lombok.Getter;
import uk.co.joesharpcs.gaming.utils.StringUtils;

/**
 * General form of a game board which is a grid.
 *
 * @param <T> The type of data stored in each cell of the grid.
 */
@Getter
public class GridBoard<T> {
  private final List<List<T>> contents;
  private final List<List<T>> previousContents;

  public GridBoard() {
    contents = new ArrayList<>();
    previousContents = new ArrayList<>();
  }

  public GridBoard(List<List<T>> contents) {
    this.contents = new ArrayList<>();
    this.previousContents = new ArrayList<>();

    for (List<T> sourceRow : contents) {
      var row = new ArrayList<T>();
      var prevRow = new ArrayList<T>();
      for (T t : sourceRow) {
        row.add(t);
        prevRow.add(t);
      }
      this.contents.add(row);
      this.previousContents.add(prevRow);
    }
  }

  public GridBoard(int dimension, T emptyValue) {
    this.contents = new ArrayList<>();
    this.previousContents = new ArrayList<>();

    for (int i = 0; i < dimension; i++) {
      var row = new ArrayList<T>();
      var prevRow = new ArrayList<T>();
      for (int j = 0; j < dimension; j++) {
        row.add(emptyValue);
        prevRow.add(emptyValue);
      }
      this.contents.add(row);
      this.previousContents.add(prevRow);
    }
  }

  public int getNumberRows() {
    return contents.size();
  }

  public Map<T, AtomicInteger> getValueCounts() {
    Map<T, AtomicInteger> counts = new HashMap<>();

    this.contents.forEach(
        row ->
            row.forEach(
                v -> counts.computeIfAbsent(v, (_v) -> new AtomicInteger(0)).incrementAndGet()));

    return counts;
  }

  private static <T> void copyContents(List<List<T>> source, List<List<T>> destination) {
    for (int row = 0; row < source.size(); row++) {
      var srcRow = source.get(row);
      var destRow = destination.get(row);

      for (int col = 0; col < srcRow.size(); col++) {
        destRow.set(col, srcRow.get(col));
      }
    }
  }

  public void restore() {
    copyContents(previousContents, contents);
  }

  public void save() {
    copyContents(contents, previousContents);
  }

  public void set(int row, int col, T value) {
    contents.get(row).set(col, value);
  }

  public T get(int row, int col) {
    return contents.get(row).get(col);
  }

  public T getPrevious(int row, int col) {
    return previousContents.get(row).get(col);
  }

  public Stream<BoardLocation> iterateBoardLocations() {
    Stream.Builder<BoardLocation> builder = Stream.builder();

    for (int row = 0; row < contents.size(); row++) {
      for (int col = 0; col < contents.get(row).size(); col++) {
        builder.accept(new BoardLocation(row, col));
      }
    }

    return builder.build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GridBoard<?> that = (GridBoard<?>) o;
    return Objects.equals(contents, that.contents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contents);
  }

  public String toString(Function<T, String> converter) {
    StringJoiner rowJoiner = new StringJoiner("\n");

    for (List<T> content : contents) {
      StringJoiner cellJoiner = new StringJoiner("");
      content.stream().map(converter).forEach(cellJoiner::add);
      rowJoiner.add(cellJoiner.toString());
    }

    return rowJoiner.toString();
  }

  public String toPrintableString(Function<T, String> converter) {
    StringJoiner rowJoiner = new StringJoiner("\n");

    StringJoiner headingJoiner = new StringJoiner(" ");
    headingJoiner.add(".");
    for (int i = 0; i < contents.size(); i++) {
      headingJoiner.add(Integer.toString(i, 10));
    }
    rowJoiner.add(headingJoiner.toString());

    for (int i = 0; i < contents.size(); i++) {
      StringJoiner cellJoiner = new StringJoiner(" ");
      cellJoiner.add(Integer.toString(i, 10));
      contents.get(i).stream().map(converter).forEach(cellJoiner::add);
      rowJoiner.add(cellJoiner.toString());
    }

    return rowJoiner.toString();
  }

  public static <X> GridBoard<X> fromString(String value, Function<String, X> converter) {
    return fromString(value, converter, ShapeConstraint.NONE);
  }

  public static <X> GridBoard<X> fromString(
      String value, Function<String, X> converter, ShapeConstraint shapeConstraint) {
    List<List<X>> contents =
        Arrays.stream(value.split("\n"))
            .filter(StringUtils::isNotBlank)
            .map(String::trim)
            .map(
                row ->
                    Arrays.stream(row.split(""))
                        .map(String::trim)
                        .filter(StringUtils::isNotBlank)
                        .map(converter)
                        .toList())
            .toList();

    if (!shapeConstraint.validate(contents)) {
      throw new InvalidBoardStateStringException("Board should be a square");
    }

    return new GridBoard<>(contents);
  }
}
