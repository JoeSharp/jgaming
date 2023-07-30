package uk.co.joesharpcs.gaming.go;

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

    public Player nextPlayer() {
        return WHITE.equals(this) ? BLACK : WHITE;
    }
}
