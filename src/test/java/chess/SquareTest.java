package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SquareTest {

    @Test
    @DisplayName("Test both types of constructors")
    public void testConstructor(){
        Square square = new Square(5, 3);
        assertEquals(5, square.getY(), "The x-coord is incorrect.");
        assertEquals(3, square.getX(), "The y-coord is incorrect.");
        assertEquals(null, square.getPiece(), "The square is not empty");

        Square square2 = new Square(square);
        assertEquals(5, square2.getY(), "The x-coord is incorrect.");
        assertEquals(3, square2.getX(), "The y-coord is incorrect.");
        assertNull(square2.getPiece(), "The square is not empty");
    }

    @Test
    @DisplayName("Test getters/setters")
    public void testSetGet() {
        Square square = new Square(5, 3);
        Board board = new Board(false);
        Piece piece = new Pawn(square, true, false); 
        square.setBoard(board);
        square.setPiece(piece);
        assertEquals(board, square.getBoard());
        assertEquals(piece, square.getPiece());
    }

    

}
