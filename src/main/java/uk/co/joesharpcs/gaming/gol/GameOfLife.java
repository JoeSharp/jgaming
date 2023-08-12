package uk.co.joesharpcs.gaming.gol;

public class GameOfLife {
    public static final int DEFAULT_DIMENSION = 100;

    private final int dimension;

    public GameOfLife() {
        this(DEFAULT_DIMENSION);
    }

    public GameOfLife(int dimension) {
        this.dimension = dimension;
    }

    public boolean isAlive(int row, int col) {
        return false;
    }

    public static GameOfLife fromString(final String value) {
        return new GameOfLife();
    }
}
