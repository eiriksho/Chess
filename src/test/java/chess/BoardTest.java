package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    @DisplayName("Test board constructor")
    public void testConstructor(){
        Board board = new Board(false);
        assertEquals('R', board.getBoard()[0][0].getPiece().getType());
        assertEquals('N', board.getBoard()[0][1].getPiece().getType());
        assertEquals('B', board.getBoard()[0][2].getPiece().getType());
        assertEquals('Q', board.getBoard()[0][3].getPiece().getType());
        assertEquals('K', board.getBoard()[0][4].getPiece().getType());
        assertEquals('P', board.getBoard()[1][0].getPiece().getType());
        assertEquals('R', board.getBoard()[7][0].getPiece().getType());
        assertEquals('N', board.getBoard()[7][1].getPiece().getType());
        assertEquals('B', board.getBoard()[7][2].getPiece().getType());
        assertEquals('Q', board.getBoard()[7][3].getPiece().getType());
        assertEquals('K', board.getBoard()[7][4].getPiece().getType());
        assertEquals('P', board.getBoard()[6][0].getPiece().getType());
        assertNull(board.getBoard()[3][0].getPiece());
        assertEquals(board.getBoard()[0][4], board.getWhiteKing());
        assertEquals(board.getBoard()[7][4], board.getBlackKing());

        Board empty = new Board(true);
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                assertNull(empty.getBoard()[i][j].getPiece());
            }
        }
    }

    @Test
    @DisplayName("Test deepcopy")
    public void testDeepCopy() {
        Board board = new Board(false);
        Board shadowBoard = new Board(board);
        assertNotEquals(board, shadowBoard);
        assertEquals(board.getBoard()[1][0].getPiece(), shadowBoard.getBoard()[1][0].getPiece());
        assertNotEquals(board.getBoard()[1][0], shadowBoard.getBoard()[1][0]);
        shadowBoard.move(shadowBoard.getBoard()[1][0], shadowBoard.getBoard()[3][0]);
        assertNotEquals(board.getBoard()[1][0].getPiece(), shadowBoard.getBoard()[1][0].getPiece());
        Board shadowBoard2 = new Board(shadowBoard);
        assertNotEquals(shadowBoard2.getBoard()[1][0].getPiece(), board.getBoard()[1][0].getPiece());
    }

    @Test
    @DisplayName("Test setters/getters") 
    public void testSetGet() {
        Board board = new Board(false);
        board.setBlackKing(board.getBoard()[3][3]);
        assertEquals(board.getBoard()[3][3], board.getBlackKing());
        board.setWhiteKing(board.getBoard()[4][4]);
        assertEquals(board.getBoard()[4][4], board.getWhiteKing());
        assertTrue(board.getTurn());
        board.setTurn(false);
        assertFalse(board.getTurn());
    }

    @Test
    @DisplayName("Test Illegal moves")
    public void testMoves() {
        Board board = new Board(false);
        assertNull(board.getBoard()[2][2].getPiece());                                                           //checking square empty
        assertEquals("Trekket er gjort", board.move(board.getBoard()[1][2], board.getBoard()[2][2]));   //move pawn
        assertEquals('P', board.getBoard()[2][2].getPiece().getType());                                 //check sqaure now contains pawn
        assertNull(board.getBoard()[1][2].getPiece());                                                           //check original square now empty

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			board.move(board.getBoard()[2][2], board.getBoard()[2][2]);           
		}, "valid move");
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			board.move(board.getBoard()[2][2], board.getBoard()[2][4]);           
		}, "piece legit");
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			board.move(board.getBoard()[0][0], board.getBoard()[0][4]);           
		}, "occupied");

        board.move(board.getBoard()[0][3], board.getBoard()[3][0]);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			board.move(board.getBoard()[6][3], board.getBoard()[5][3]);           
		}, "is Check");

        Board castlingBoard = new Board(true);
        Piece whiteKing = new King(castlingBoard.getBoard()[0][4], true);
        Piece whiteRook = new Rook(castlingBoard.getBoard()[0][0], true);
        Piece blackRook = new Rook(castlingBoard.getBoard()[1][2], false);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			castlingBoard.move(board.getBoard()[0][4], board.getBoard()[0][2]);           
		}, "castling");
               

    }

    @Test
    @DisplayName("Test check/checkmate")
    public void testCheck() {
        Board board = new Board(false);
        board.move(board.getBoard()[1][4],board.getBoard()[2][4]);
        board.move(board.getBoard()[6][5],board.getBoard()[5][5]);
        board.move(board.getBoard()[2][4],board.getBoard()[3][4]);
        board.move(board.getBoard()[6][7],board.getBoard()[4][7]);
        assertEquals("Sjakk", board.move(board.getBoard()[0][3],board.getBoard()[4][7]));
        board.move(board.getBoard()[6][6],board.getBoard()[5][6]);
        assertEquals("Sjakkmatt", board.move(board.getBoard()[4][7],board.getBoard()[5][6]));
    }

    @Test
    @DisplayName("Test stalemate")
    public void testStalemate() {
        Board board = new Board(false);
        board.move(board.getBoard()[1][4],board.getBoard()[2][4]);
        board.move(board.getBoard()[6][0],board.getBoard()[4][0]);
        board.move(board.getBoard()[0][3],board.getBoard()[4][7]);
        board.move(board.getBoard()[7][0],board.getBoard()[5][0]);
        board.move(board.getBoard()[4][7],board.getBoard()[4][0]);
        board.move(board.getBoard()[6][7],board.getBoard()[4][7]);
        board.move(board.getBoard()[1][7],board.getBoard()[3][7]);
        board.move(board.getBoard()[5][0],board.getBoard()[5][7]);
        board.move(board.getBoard()[4][0],board.getBoard()[6][2]);
        board.move(board.getBoard()[6][5],board.getBoard()[5][5]);
        board.move(board.getBoard()[6][2],board.getBoard()[6][3]);
        board.move(board.getBoard()[7][4],board.getBoard()[6][5]);
        board.move(board.getBoard()[6][3],board.getBoard()[6][1]);
        board.move(board.getBoard()[7][3],board.getBoard()[2][3]);
        board.move(board.getBoard()[6][1],board.getBoard()[7][1]);
        board.move(board.getBoard()[2][3],board.getBoard()[6][7]);
        board.move(board.getBoard()[7][1],board.getBoard()[7][2]);
        board.move(board.getBoard()[6][5],board.getBoard()[5][6]);
        assertEquals("Patt", board.move(board.getBoard()[7][2],board.getBoard()[5][4]));
    }
}
