package uk.co.joesharpcs.gaming.board;

import uk.co.joesharpcs.gaming.utils.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenericBoard<T> {
    private final List<List<BoardSlot<T>>> contents;

    public GenericBoard(List<List<T>> contents) {
        this.contents = contents.stream().map(row -> row.stream().map(BoardSlot::new).toList()).toList();
    }

    public GenericBoard(int dimension, T emptyValue) {
        this.contents = new ArrayList<>();

        for (int i=0; i<dimension; i++) {
            var row = new ArrayList<BoardSlot<T>>();
            for (int j=0; j<dimension; j++) {
                row.add(new BoardSlot<>(emptyValue));
            }
            this.contents.add(row);
        }
    }

    public int getNumberRows() {
        return contents.size();
    }

    public Map<T, AtomicInteger> getValueCounts() {
        Map<T, AtomicInteger> counts = new HashMap<>();

        this.contents.forEach(row -> row.forEach(v ->
            counts.computeIfAbsent(v.get(),
                    (_v) -> new AtomicInteger(0))
                    .incrementAndGet()
        ));

        return counts;
    }

    public void restore() {
        contents.forEach(row -> row.forEach(BoardSlot::restore));
    }

    public void save() {
        contents.forEach(row -> row.forEach(BoardSlot::save));
    }

    public void set(int row, int col, T value) {
        contents.get(row).get(col).set(value);
    }

    public T get(int row, int col) {
        return contents.get(row).get(col).get();
    }

    public T getPrevious(int row, int col) {
        return contents.get(row).get(col).getPrevious();
    }

    public Stream<BoardLocation> iterateBoardLocations() {
        Stream.Builder<BoardLocation> builder = Stream.builder();

        for (int row=0; row< contents.size(); row++) {
            for (int col=0; col<contents.get(row).size(); col++) {
                builder.accept(new BoardLocation(row, col));
            }
        }

        return builder.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericBoard<?> that = (GenericBoard<?>) o;
        return Objects.equals(contents, that.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contents);
    }

    @Override
    public String toString() {
        StringJoiner rowJoiner = new StringJoiner("\n");

        StringJoiner headingJoiner = new StringJoiner(" ");
        headingJoiner.add(".");
        for (int i=0; i< contents.size(); i++) {
            headingJoiner.add(Integer.toString(i, 10));
        }
        rowJoiner.add(headingJoiner.toString());

        for (int i=0; i< contents.size(); i++) {
            StringJoiner cellJoiner = new StringJoiner(" ");
            cellJoiner.add(Integer.toString(i,10));
            contents.get(i).stream()
                    .map(Object::toString)
                    .forEach(cellJoiner::add);
            rowJoiner.add(cellJoiner.toString());
        }

        return rowJoiner.toString();
    }

    public static <X>GenericBoard<X> fromString(String value, Function<String, X> converter) {
        return fromString(value, converter, ShapeConstraint.NONE);
    }

    public static <X>GenericBoard<X> fromString(String value,
                                                Function<String, X> converter,
                                                ShapeConstraint shapeConstraint) {
        List<List<X>> contents = Arrays
                .stream(value.split("\n"))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .map(row -> Arrays.stream(row.split(""))
                        .map(String::trim)
                        .filter(StringUtils::isNotBlank)
                        .map(converter)
                        .toList())
                .toList();

        if (!shapeConstraint.validate(contents)) {
            throw new InvalidBoardStateStringException("Board should be a square");
        }

        return new GenericBoard<>(contents);
    }
}
