package uk.co.joesharpcs.gaming.tictactoe;

public enum TicTacToePlayer {
    NOUGHTS(TicTacToePointValue.NOUGHT),
    CROSSES(TicTacToePointValue.CROSS);

    private final TicTacToePointValue pointValue;

    TicTacToePlayer(TicTacToePointValue pointValue) {
        this.pointValue = pointValue;
    }

    public TicTacToePlayer otherPlayer() {
        return NOUGHTS.equals(this) ? CROSSES : NOUGHTS;
    }

    public TicTacToePointValue getPointValue() {
        return pointValue;
    }
}
