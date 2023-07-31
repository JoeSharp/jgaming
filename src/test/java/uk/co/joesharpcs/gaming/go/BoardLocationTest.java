package uk.co.joesharpcs.gaming.go;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.BiConsumer;

public class BoardLocationTest {
    @Test
    public void topLeft() {
        // Given
        BiConsumer<Integer, Integer> receiver = Mockito.mock();

        // When
        BoardLocation.getConnected(4, 0, 0, receiver);

        // Then
        Mockito.verify(receiver).accept(1, 0);
        Mockito.verify(receiver).accept(0, 1);
    }

    @Test
    public void topRight() {
        // Given
        BiConsumer<Integer, Integer> receiver = Mockito.mock();
        
        // When
        BoardLocation.getConnected(4, 0, 3, receiver);

        // Then
        Mockito.verify(receiver).accept(1, 3);
        Mockito.verify(receiver).accept(0, 2);
    }

    @Test
    public void topRow() {
        // Given
        BiConsumer<Integer, Integer> receiver = Mockito.mock();
        
        // When
        BoardLocation.getConnected(4, 0, 1, receiver);

        // Then
        Mockito.verify(receiver).accept(1, 1);
        Mockito.verify(receiver).accept(0, 0);
        Mockito.verify(receiver).accept(0, 2);
    }


    @Test
    public void bottomLeft() {
        // Given
        BiConsumer<Integer, Integer> receiver = Mockito.mock();

        // When
        BoardLocation.getConnected(4, 3, 0, receiver);

        // Then
        Mockito.verify(receiver).accept(2, 0);
        Mockito.verify(receiver).accept(3, 1);
    }

    @Test
    public void bottomRight() {
        // Given
        BiConsumer<Integer, Integer> receiver = Mockito.mock();

        // When
        BoardLocation.getConnected(4, 3, 3, receiver);

        // Then
        Mockito.verify(receiver).accept(2, 3);
        Mockito.verify(receiver).accept(3, 2);
    }

    @Test
    public void bottomRow() {
        // Given
        BiConsumer<Integer, Integer> receiver = Mockito.mock();
        
        // When
        BoardLocation.getConnected(4, 3, 1, receiver);

        // Then
        Mockito.verify(receiver).accept(3, 0);
        Mockito.verify(receiver).accept(3, 2);
        Mockito.verify(receiver).accept(2, 1);
    }


    @Test
    public void leftEdge() {
        // Given
        BiConsumer<Integer, Integer> receiver = Mockito.mock();

        // When
        BoardLocation.getConnected(4, 2, 0, receiver);

        // Then
        Mockito.verify(receiver).accept(2, 1);
        Mockito.verify(receiver).accept(1, 0);
        Mockito.verify(receiver).accept(3, 0);
    }

    @Test
    public void rightEdge() {
        // Given
        BiConsumer<Integer, Integer> receiver = Mockito.mock();

        // When
        BoardLocation.getConnected(4, 2, 3, receiver);

        // Then
        Mockito.verify(receiver).accept(2, 2);
        Mockito.verify(receiver).accept(1, 3);
        Mockito.verify(receiver).accept(3, 3);
    }
    @Test
    public void middle() {
        // Given
        BiConsumer<Integer, Integer> receiver = Mockito.mock();

        // When
        BoardLocation.getConnected(4, 2, 2, receiver);

        // Then
        Mockito.verify(receiver).accept(2, 1);
        Mockito.verify(receiver).accept(2, 3);
        Mockito.verify(receiver).accept(1, 2);
        Mockito.verify(receiver).accept(3, 2);
    }
}
