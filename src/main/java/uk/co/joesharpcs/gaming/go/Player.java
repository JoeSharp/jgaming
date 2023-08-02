package uk.co.joesharpcs.gaming.go;

import java.util.Arrays;
import java.util.Optional;

public enum Player {
    WHITE(PointValue.WHITE),
    BLACK(PointValue.BLACK);

    private final PointValue pointValue;

    Player(PointValue pointValue) {
        this.pointValue = pointValue;
    }

    public PointValue getPointValue() {
        return this.pointValue;
    }

    public Player otherPlayer() {
        return WHITE.equals(this) ? BLACK : WHITE;
    }

    static Optional<Player> fromPointValue(PointValue pointValue) {
        return Arrays.stream(values())
                .filter(p -> pointValue.equals(p.getPointValue()))
                .findAny();
    }
}
