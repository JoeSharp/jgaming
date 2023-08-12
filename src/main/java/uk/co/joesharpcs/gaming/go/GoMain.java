package uk.co.joesharpcs.gaming.go;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GoMain {

    public static void main(String[] args) {

        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        GoBoard board = new GoBoard(9);

        System.out.println("Let's play Go!");

        while (board.gameUnderway()) {
            System.out.printf("It's %s turn to move%n", board.getWhosTurn());
            System.out.println(board.toStringBoard());

            System.out.println("Type in coordinates in form <row>,<col>");
            try {
                String rawLine = reader.readLine();
                BoardLocation location = BoardLocation.fromString(rawLine);
                board.move(location);
            } catch (Exception e) {
                System.err.println("Failed to read line from user: " + e.getLocalizedMessage());
            }
        }
    }
}
