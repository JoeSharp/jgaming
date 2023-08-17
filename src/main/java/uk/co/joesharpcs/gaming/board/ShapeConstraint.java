package uk.co.joesharpcs.gaming.board;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ShapeConstraint {
    SQUARE,
    RECTANGLE,
    NONE;

    public <X> boolean validate(List<List<X>> contents) {
        return switch (this) {
            case SQUARE -> isSquare(contents);
            case RECTANGLE -> isRectangle(contents);
            case NONE -> true;
        };
    }

    private static <X> boolean isSquare(List<List<X>> contents) {
        int size = contents.size();

        for (var row : contents) {
            if (row.size() != size) {
                return false;
            }
        }

        return true;
    }

    private static <X> boolean isRectangle(List<List<X>> contents) {
        return contents.stream()
                .map(List::size)
                .collect(Collectors.toSet()).size() == 1;
    }
}
