package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SquareIteratorTest {
    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        Board board = new Board(false);
        SquareIterator iterator = new SquareIterator(board.getBoard());
        assertEquals(iterator.next(), board.getBoard()[0][0]);
    }

    @Test
    @DisplayName("Test hasNext()")
    public void testHasNext() {
        Board board = new Board(false);
        SquareIterator iterator = new SquareIterator(board.getBoard());
        for(int i = 0; i<64; i++){
            assertTrue(iterator.hasNext());
            iterator.next();
        }
        assertFalse(iterator.hasNext()); 

    }

    @Test
    @DisplayName("Test next()")
    public void testNext() {
        Board board = new Board(false);
        SquareIterator iterator = new SquareIterator(board.getBoard());
        assertEquals(iterator.next(), board.getBoard()[0][0]);
        assertNotEquals(iterator.next(), board.getBoard()[0][0]);
    }
}
