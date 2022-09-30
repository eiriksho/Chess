package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileTreaterTest {
    
    @Test
    @DisplayName("Test save/load")
    public void testSave() throws FileNotFoundException {
        Board board_saved = new Board(true);                                //oppretter brett og plasserer brikker
        Piece blackKing = new King(board_saved.getBoard()[0][1], false);
        Piece whiteKing = new King(board_saved.getBoard()[7][6], true);
        Piece pawn = new Pawn(board_saved.getBoard()[1][1], true, true);
        Piece rook = new Rook(board_saved.getBoard()[2][5], false);
        Piece knight = new Knight(board_saved.getBoard()[6][4], false);
        Piece bishop = new Bishop(board_saved.getBoard()[3][3], true);
        Piece queen = new Queen(board_saved.getBoard()[0][5], true);
        FileTreater treater = new FileTreater();
        treater.save(board_saved);                                                 //lagrer og laster opp brettet 
        Board board_loaded = treater.load();                                       //sjekker at tilstanden er lik på begge brett
        assertEquals(board_saved.getBoard()[0][1].getPiece().getType(), board_loaded.getBoard()[0][1].getPiece().getType());
        assertEquals(board_saved.getBoard()[0][1].getPiece().getColor(), board_loaded.getBoard()[0][1].getPiece().getColor());
        assertEquals(board_saved.getBlackKing().getPiece().getType(), board_loaded.getBoard()[0][1].getPiece().getType());
        assertEquals(board_saved.getBlackKing().getPiece().getColor(), board_loaded.getBoard()[0][1].getPiece().getColor());
        assertEquals(board_saved.getBoard()[7][6].getPiece().getType(), board_loaded.getBoard()[7][6].getPiece().getType());
        assertEquals(board_saved.getBoard()[7][6].getPiece().getColor(), board_loaded.getBoard()[7][6].getPiece().getColor());
        assertEquals(board_saved.getWhiteKing().getPiece().getType(), board_loaded.getBoard()[7][6].getPiece().getType());
        assertEquals(board_saved.getWhiteKing().getPiece().getColor(), board_loaded.getBoard()[7][6].getPiece().getColor());
        assertEquals(board_saved.getBoard()[1][1].getPiece().getType(), board_loaded.getBoard()[1][1].getPiece().getType());
        assertEquals(board_saved.getBoard()[1][1].getPiece().getColor(), board_loaded.getBoard()[1][1].getPiece().getColor());
        assertEquals(board_saved.getBoard()[1][1].getPiece().getIsMoved(), board_loaded.getBoard()[1][1].getPiece().getIsMoved());
        assertEquals(board_saved.getBoard()[2][5].getPiece().getType(), board_loaded.getBoard()[2][5].getPiece().getType());
        assertEquals(board_saved.getBoard()[2][5].getPiece().getColor(), board_loaded.getBoard()[2][5].getPiece().getColor());
        assertEquals(board_saved.getBoard()[6][4].getPiece().getType(), board_loaded.getBoard()[6][4].getPiece().getType());
        assertEquals(board_saved.getBoard()[6][4].getPiece().getColor(), board_loaded.getBoard()[6][4].getPiece().getColor());
        assertEquals(board_saved.getBoard()[3][3].getPiece().getType(), board_loaded.getBoard()[3][3].getPiece().getType());
        assertEquals(board_saved.getBoard()[3][3].getPiece().getColor(), board_loaded.getBoard()[3][3].getPiece().getColor());
        assertEquals(board_saved.getBoard()[0][5].getPiece().getType(), board_loaded.getBoard()[0][5].getPiece().getType());
        assertEquals(board_saved.getBoard()[0][5].getPiece().getColor(), board_loaded.getBoard()[0][5].getPiece().getColor());
    }
}
