package chess;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    @DisplayName("Test constructors") 
    public void testConstructors() {
        Board board = new Board(true);
        Piece pawn = new Pawn(board.getBoard()[0][0], true, true);
        assertEquals('P', pawn.getType());
        assertEquals('P', board.getBoard()[0][0].getPiece().getType());
        assertTrue(pawn.getColor());
        assertTrue(pawn.getIsMoved());
        Piece rook = new Rook(board.getBoard()[1][1], true);
        assertEquals('R', rook.getType());
        assertEquals('R', board.getBoard()[1][1].getPiece().getType());
        assertTrue(rook.getColor());
        assertFalse(rook.getIsMoved());
        Piece knight = new Knight(board.getBoard()[4][7], false);
        assertEquals('N', knight.getType());
        assertEquals('N', board.getBoard()[4][7].getPiece().getType());
        assertFalse(knight.getColor());
        assertFalse(knight.getIsMoved());
        Piece bishop = new Bishop(board.getBoard()[6][3], true);
        assertEquals('B', bishop.getType());
        assertEquals('B', board.getBoard()[6][3].getPiece().getType());
        assertTrue(bishop.getColor());
        assertFalse(bishop.getIsMoved());
        Piece queen = new Queen(board.getBoard()[2][0], false);
        assertEquals('Q', queen.getType());
        assertEquals('Q', board.getBoard()[2][0].getPiece().getType());
        assertFalse(queen.getColor());
        assertFalse(queen.getIsMoved());
        Piece whiteKing = new King(board.getBoard()[5][4], true);
        assertEquals('K', whiteKing.getType());
        assertEquals('K', board.getBoard()[5][4].getPiece().getType());
        assertTrue(whiteKing.getColor());
        assertFalse(whiteKing.getIsMoved());
        assertEquals(whiteKing, board.getWhiteKing().getPiece());
        Piece blackKing = new King(board.getBoard()[7][4], false);
        assertEquals('K', blackKing.getType());
        assertEquals('K', board.getBoard()[7][4].getPiece().getType());
        assertFalse(blackKing.getColor());
        assertFalse(blackKing.getIsMoved());
        assertEquals(blackKing, board.getBlackKing().getPiece());
    }

    @Test
    @DisplayName("Test canMove")
    public void testCanMove() {
        Board board = new Board(true);
        Piece whiteKing = new King(board.getBoard()[3][3], true);
        Piece blackKing = new King(board.getBoard()[7][7], false);
        Piece attacker = new Bishop(board.getBoard()[0][0], false);
        Piece defender = new Rook(board.getBoard()[6][2], true);
        assertTrue(defender.canMove(board.getBoard()[6][2], board.getBoard()[2][2]));       //sjekker at tårnet kan stoppe sjakk
        assertFalse(defender.canMove(board.getBoard()[6][2], board.getBoard()[6][1]));      //sjekker at tårnet ikke kan bevege seg en annen plass
        assertTrue(whiteKing.canMove(board.getBoard()[3][3], board.getBoard()[2][3]));      //sjekker at kongen kan rømme
        assertFalse(whiteKing.canMove(board.getBoard()[3][3], board.getBoard()[4][4]));     //sjekker at kongen ikke kan sette seg selv i sjakk
    }

    @Test
    @DisplayName("Test isStaleMate")
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
        board.move(board.getBoard()[7][2],board.getBoard()[5][4]);
        assertTrue(board.getBoard()[0][0].getPiece().isStaleMate(board.getBoard(), true));
        assertFalse(board.getBoard()[0][0].getPiece().isStaleMate(board.getBoard(), false));
    }

    @Test
    @DisplayName("Test isCheckMate")
    public void checkMate() throws FileNotFoundException {
        Board case1 = new Board(true);
        Piece pawn1 = new Pawn(case1.getBoard()[1][5], true, false);
        Piece pawn2 = new Pawn(case1.getBoard()[1][6], true, false);
        Piece pawn3 = new Pawn(case1.getBoard()[1][7], true, false);
        Piece whiteKing = new King(case1.getBoard()[0][6], true);
        Piece blackKing = new King(case1.getBoard()[7][7], false);
        Piece attacker = new Rook(case1.getBoard()[0][2], false);
        assertTrue(whiteKing.isCheckMate(case1.getBoard()[0][2]));                          //sjekker matt
        Piece defender = new Bishop(case1.getBoard()[2][0], true);
        assertFalse(whiteKing.isCheckMate(case1.getBoard()[0][2]));                         //sjekker at det ikke lenger er matt dersom man kan ta brikken som truer kongen
        Piece occupier = new Pawn(case1.getBoard()[1][1], true, false);
        Piece defender2 = new Bishop(case1.getBoard()[3][1], true);
        assertFalse(whiteKing.isCheckMate(case1.getBoard()[0][2]));                         //sjekker at det ikke er matt dersom du kan sette en brikke i veien
        Piece occupier2 = new Pawn(case1.getBoard()[2][2], true, true);
        assertTrue(whiteKing.isCheckMate(case1.getBoard()[0][2]));
        case1.getBoard()[1][7].setPiece(null);
        assertFalse(whiteKing.isCheckMate(case1.getBoard()[0][2]));                         //sjekker at det ikke er matt dersom kongen kan flytte

        Board case2 = new Board(false);
        case2.move(case2.getBoard()[1][5], case2.getBoard()[2][5]);
        case2.move(case2.getBoard()[6][4], case2.getBoard()[5][4]);
        case2.move(case2.getBoard()[1][6], case2.getBoard()[3][6]);
        case2.move(case2.getBoard()[7][3], case2.getBoard()[3][7]);
        assertTrue(case2.getBlackKing().getPiece().isCheckMate(case2.getBoard()[3][7]));    //sjekker matt av konge med motsatt farge
    }

    @Test
    @DisplayName("Test isCheck")
    public void testIsCheck() {
        Board board = new Board(true);
        Piece white_king = new King(board.getBoard()[3][3], true);
        Piece black_king = new King(board.getBoard()[7][3], false);
        Piece pawn = new Pawn(board.getBoard()[6][2], false, false);
        Piece rook = new Rook(board.getBoard()[2][7], false);
        Piece knight = new Knight(board.getBoard()[7][5], false);
        Piece bishop = new Bishop(board.getBoard()[1][7], false);
        Piece queen = new Queen(board.getBoard()[0][2], false);
        assertFalse(white_king.isCheck(true, board));
        assertFalse(white_king.isCheck(false, board));
        board.move(board.getBoard()[6][2], board.getBoard()[4][2]);
        assertTrue(white_king.isCheck(true, board));                //setter i sjakk med bonde
        board.move(board.getBoard()[4][2], board.getBoard()[3][2]);
        assertFalse(white_king.isCheck(true, board));
        board.move(board.getBoard()[2][7], board.getBoard()[3][7]);
        assertTrue(white_king.isCheck(true, board));                //setter i sjakk med tårn
        Piece occupier1 = new Pawn(board.getBoard()[3][6], true, false);
        assertFalse(white_king.isCheck(true, board));               //sjekker at det ikke er sjakk gjennom brikker
        board.move(board.getBoard()[7][5], board.getBoard()[5][4]);
        assertTrue(white_king.isCheck(true, board));                //setter i sjakk med springer
        board.move(board.getBoard()[5][4], board.getBoard()[7][5]);
        board.move(board.getBoard()[1][7], board.getBoard()[0][6]);
        assertTrue(white_king.isCheck(true, board));                //setter i sjakk med løper
        Piece occupier2 = new Pawn(board.getBoard()[1][5], true, false);
        assertFalse(white_king.isCheck(true, board));               //sjekker at det ikke er sjakk gjennom brikker
        board.move(board.getBoard()[0][2], board.getBoard()[0][3]);
        assertTrue(white_king.isCheck(true, board));                //setter i sjakk med dronning
        Piece occupier3 = new Pawn(board.getBoard()[1][3], true, false);
        assertFalse(white_king.isCheck(true, board));               //sjekker at det ikke er sjakk gjennom brikker
        Piece whiteQueen = new Queen(board.getBoard()[6][0], true);
        Piece whiteRook = new Rook(board.getBoard()[5][3], true);
        Piece whiteKnight = new Knight(board.getBoard()[4][1], true);
        Piece whiteBishop = new Bishop(board.getBoard()[0][0], true);
        assertFalse(white_king.isCheck(true, board));
        assertTrue(white_king.isCheck(false, board));
    }

    @Test
    @DisplayName("Test canCheck")
    public void testCanCheck() {
        Board board = new Board(true);
        Piece king = new King(board.getBoard()[3][3], true);
        Piece partner = new Queen(board.getBoard()[5][5], true);
        Piece occupier = new Pawn(board.getBoard()[1][1], false, false);
        Piece killer = new Rook(board.getBoard()[3][7], false);
        Piece blocked_enemy = new Bishop(board.getBoard()[0][0], false); 
        Piece dumb_enemy = new Knight(board.getBoard()[4][1], false);
        assertFalse(partner.canCheck(board.getBoard()[0][0], board.getBoard()[3][3]));        //not valid move
        assertFalse(dumb_enemy.canCheck(board.getBoard()[3][7], board.getBoard()[3][3]));     //not piece legit
        assertFalse(blocked_enemy.canCheck(board.getBoard()[4][1], board.getBoard()[3][3]));  //occupied
        assertTrue(killer.canCheck(board.getBoard()[3][7], board.getBoard()[3][3]));          //OK
    }

    @Test
    @DisplayName("Test valid moves")
    public void testValidMove() {
        Board board = new Board(true);
        Piece occupier = new Pawn(board.getBoard()[1][1], true, false);
        Piece rook = new Rook(board.getBoard()[1][5], true);
        assertTrue(rook.validMove(board.getBoard()[1][5], board.getBoard()[1][7]));     //prøver et gyldig trekk
        assertFalse(rook.validMove(board.getBoard()[1][5], board.getBoard()[1][5]));    //prøver å flytte tilbake til start
        assertFalse(rook.validMove(board.getBoard()[1][5], board.getBoard()[1][1]));    //prøver å flytte på egen brikke
        assertFalse(rook.validMove(board.getBoard()[3][5], board.getBoard()[1][5]));    //prøver å flytte fra et tomt felt
    }

    @Test
    @DisplayName("Test setters and getters")
    public void testSetGet() {
        Board board = new Board(true);
        Piece whiteKing = new King(board.getBoard()[5][7], true);   //setter inn konger for å få lov til å gjøre trekk
        Piece blackKing = new King(board.getBoard()[7][7], false);
        Piece pawn = new Pawn(board.getBoard()[1][1], false, false);
        Piece rook = new Rook(board.getBoard()[4][1], false);
        assertFalse(pawn.getColor());                               //sjekker at getColor() gir riktig farge
        pawn.setColor(true);                                  //endrer fargen med setColor()
        assertTrue(pawn.getColor());                                //sjekker at getColor() returnerer oppdatert farge
        assertFalse(pawn.getIsMoved());                             //sjekker at tårnet ikke har blitt flyttet
        board.move(board.getBoard()[4][1], board.getBoard()[1][1]); //flytter tårnet/tar bonden med tårnet
        assertTrue(rook.getIsMoved());                              //sjekker at brikken har blitt flyttet
        pawn.setIsMoved(true);                              //sjekker setter/getter på isMoved()
        assertTrue(pawn.getIsMoved());
        assertEquals('R', rook.getType());                 //sjekker setter/getter på type
        rook.setType('s');
        assertEquals('s', rook.getType());
    }

    @Test
    @DisplayName("Test changeToQueen")
    public void testChangeToQueen(){
        Board board = new Board(true);
        Piece whiteKing = new King(board.getBoard()[5][7], true);   //setter inn konger for å få lov til å gjøre trekk
        Piece blackKing = new King(board.getBoard()[7][7], false);
        Piece pawn = new Pawn(board.getBoard()[6][0], true, true);
        assertEquals('P', board.getBoard()[6][0].getPiece().getType());
        board.move(board.getBoard()[6][0], board.getBoard()[7][0]);
        assertEquals('Q', board.getBoard()[7][0].getPiece().getType());
    }

    @Test
    @DisplayName("Test castling")
    public void testCastling(){
        Board board = new Board(true);
        Piece whiteKing = new King(board.getBoard()[0][4], true);   //setter inn konger for å få lov til å gjøre trekk
        Piece blackKing = new King(board.getBoard()[7][4], false);
        Piece whiteRook1 = new Rook(board.getBoard()[0][0], true);
        Piece whiteRook2 = new Rook(board.getBoard()[0][7], true);
        Piece blackRook1 = new Rook(board.getBoard()[7][0], false);
        Piece blackRook2 = new Rook(board.getBoard()[7][7], false);
        assertTrue(whiteKing.castling(board.getBoard()[0][4], board.getBoard()[0][2]));
        assertTrue(whiteKing.castling(board.getBoard()[0][4], board.getBoard()[0][6]));
        assertTrue(blackKing.castling(board.getBoard()[7][4], board.getBoard()[7][2]));
        assertTrue(blackKing.castling(board.getBoard()[7][4], board.getBoard()[7][6]));
        Piece blackQueen = new Queen(board.getBoard()[7][3], false);
        assertFalse(whiteKing.castling(board.getBoard()[0][4], board.getBoard()[0][2]));
        assertFalse(blackKing.castling(board.getBoard()[7][4], board.getBoard()[7][2]));

    }

    @Test
    @DisplayName("Test pawn subclass")
    public void testPawnMoves() {
        Board board = new Board(true);
        Piece whiteKing = new King(board.getBoard()[5][7], true);   //setter inn konger for å få lov til å gjøre trekk
        Piece blackKing = new King(board.getBoard()[7][7], false);
        Piece black_pawn = new Pawn(board.getBoard()[6][1], false, false);
        Piece white_pawn = new Pawn(board.getBoard()[1][1], true, false);
        Piece white_victim = new Pawn(board.getBoard()[4][2], true, false);
        assertTrue(black_pawn.isPieceLegit(board.getBoard()[6][1], board.getBoard()[5][1]));    //flytter et skritt
        assertTrue(black_pawn.isPieceLegit(board.getBoard()[6][1], board.getBoard()[4][1]));    //flytter to skritt
        assertFalse(black_pawn.isPieceLegit(board.getBoard()[6][1], board.getBoard()[7][1]));   //prøver å flytte bakover
        board.move(board.getBoard()[6][1], board.getBoard()[5][1]);                             //flytter bonden
        assertFalse(black_pawn.isPieceLegit(board.getBoard()[5][1], board.getBoard()[3][1]));   //prøver å flytte bonden to skritt etter at den allerede har flyttet
        assertTrue(black_pawn.isPieceLegit(board.getBoard()[5][1], board.getBoard()[4][1]));    //flytter bonden et skritt etter å ha flyttet
        assertTrue(black_pawn.isPieceLegit(board.getBoard()[5][1], board.getBoard()[4][2]));    //flytter diagonalt fram
        assertFalse(black_pawn.isPieceLegit(board.getBoard()[5][1], board.getBoard()[6][0]));   //prøver å flytte diagonalt bak

        assertTrue(white_pawn.isPieceLegit(board.getBoard()[1][1], board.getBoard()[2][1]));    //flytter et skritt
        assertTrue(white_pawn.isPieceLegit(board.getBoard()[1][1], board.getBoard()[3][1]));    //flytter to skritt
        assertFalse(white_pawn.isPieceLegit(board.getBoard()[1][1], board.getBoard()[0][1]));   //prøver å flytte bakover

        assertTrue(black_pawn.isNotOccupied(board.getBoard()[5][1], board.getBoard()[4][2]));   //angriper motstanders bonde
        assertFalse(black_pawn.isNotOccupied(board.getBoard()[5][1], board.getBoard()[4][0]));  //prøver å angripe et tomt felt
        assertTrue(white_pawn.isNotOccupied(board.getBoard()[1][1], board.getBoard()[2][1]));   //flytter et steg
        assertTrue(white_pawn.isNotOccupied(board.getBoard()[1][1], board.getBoard()[3][1]));   //flytter to steg

        Piece occupier = new Pawn(board.getBoard()[2][1], true, false);            //setter bonde i veien
        assertFalse(white_pawn.isNotOccupied(board.getBoard()[1][1], board.getBoard()[2][1]));   //prøver å flytte på bonde
        assertFalse(white_pawn.isNotOccupied(board.getBoard()[1][1], board.getBoard()[3][1]));   //prøver å flytte gjennom bonde
        board.move(board.getBoard()[2][1], board.getBoard()[3][1]);                              //flytter bonde
        assertFalse(white_pawn.isNotOccupied(board.getBoard()[1][1], board.getBoard()[3][1]));   //prøver å flytte to felt på bonde
        


    }
    
    @Test
    @DisplayName("Test rook subclass")
    public void testRookMoves() {
        Board board = new Board(true);
        Rook rook = new Rook(board.getBoard()[3][3], true);
        assertTrue(rook.isPieceLegit(board.getBoard()[3][3], board.getBoard()[0][3]));  //flytter ned
        assertTrue(rook.isPieceLegit(board.getBoard()[3][3], board.getBoard()[7][3]));  //flytter opp
        assertTrue(rook.isPieceLegit(board.getBoard()[3][3], board.getBoard()[3][6]));  //flytter høyre
        assertTrue(rook.isPieceLegit(board.getBoard()[3][3], board.getBoard()[3][1]));  //flytter venstre
        assertFalse(rook.isPieceLegit(board.getBoard()[3][3], board.getBoard()[5][5])); //flytter diagonalt
        assertFalse(rook.isPieceLegit(board.getBoard()[3][3], board.getBoard()[6][0])); //flytter diagonalt
        assertFalse(rook.isPieceLegit(board.getBoard()[3][3], board.getBoard()[4][5])); //flytter i en L
        assertFalse(rook.isPieceLegit(board.getBoard()[3][3], board.getBoard()[1][4])); //flytter i en L

        Pawn occupier = new Pawn(board.getBoard()[1][1], false, false);

        assertFalse(rook.isNotOccupied(board.getBoard()[0][1], board.getBoard()[3][1]));    //prøver å flytte gjennom bonden    opp
        assertFalse(rook.isNotOccupied(board.getBoard()[5][1], board.getBoard()[0][1]));    //prøver å flytte gjennom bonden    ned
        assertFalse(rook.isNotOccupied(board.getBoard()[1][0], board.getBoard()[1][4]));    //prøver å flytte gjennom bonden    høyre
        assertFalse(rook.isNotOccupied(board.getBoard()[1][3], board.getBoard()[1][0]));    //prøver å flytte gjennom bonden    venstre
        assertTrue(rook.isNotOccupied(board.getBoard()[1][3], board.getBoard()[1][1]));     //tar bonden
        assertTrue(rook.isNotOccupied(board.getBoard()[0][0], board.getBoard()[0][3]));     //flytter forbi bonden vertikalt
        assertTrue(rook.isNotOccupied(board.getBoard()[0][0], board.getBoard()[3][0]));     //flytter forbi bonden horisontalt
    }

    @Test
    @DisplayName("Test knight subclass")
    public void testKnightMoves() {
        Board board = new Board(true);
        Knight knight = new Knight(board.getBoard()[4][4], true);
        assertTrue(knight.isPieceLegit(board.getBoard()[4][4], board.getBoard()[3][2]));    //flytter fra [4,4] til de 8 lovlige feltene 
        assertTrue(knight.isPieceLegit(board.getBoard()[4][4], board.getBoard()[2][3]));
        assertTrue(knight.isPieceLegit(board.getBoard()[4][4], board.getBoard()[2][5]));
        assertTrue(knight.isPieceLegit(board.getBoard()[4][4], board.getBoard()[3][6]));
        assertTrue(knight.isPieceLegit(board.getBoard()[4][4], board.getBoard()[5][6]));
        assertTrue(knight.isPieceLegit(board.getBoard()[4][4], board.getBoard()[6][5]));
        assertTrue(knight.isPieceLegit(board.getBoard()[4][4], board.getBoard()[6][3]));
        assertTrue(knight.isPieceLegit(board.getBoard()[4][4], board.getBoard()[5][2]));
        assertFalse(knight.isPieceLegit(board.getBoard()[4][4], board.getBoard()[4][7]));   //flytter horisontalt
        assertFalse(knight.isPieceLegit(board.getBoard()[4][4], board.getBoard()[7][4]));   //flytter vertikalt
        assertFalse(knight.isPieceLegit(board.getBoard()[4][4], board.getBoard()[6][6]));   //flytter diagonalt
        assertFalse(knight.isPieceLegit(board.getBoard()[4][4], board.getBoard()[3][1]));   //flytter for langt venstre
        assertFalse(knight.isPieceLegit(board.getBoard()[4][4], board.getBoard()[1][3]));   //flytter for langt ned

        Piece occupier = new Pawn(board.getBoard()[1][1], false, false);
        Piece occupier2 = new Pawn(board.getBoard()[2][1], false, false);

        assertTrue(knight.isNotOccupied(board.getBoard()[1][0], board.getBoard()[2][2]));   //sjekker at springer kan "hoppe over" brikker
        assertTrue(knight.isNotOccupied(board.getBoard()[2][3], board.getBoard()[1][1]));   //sjekker at springer kan ta andre brikker
    }

    @Test
    @DisplayName("Test bishop subclass")
    public void testBishopMoves(){
        Board board = new Board(true);
        Bishop bishop = new Bishop(board.getBoard()[0][0], true);
        assertTrue(bishop.isPieceLegit(board.getBoard()[0][0], board.getBoard()[3][3]));    //flytter opp/høyre
        assertTrue(bishop.isPieceLegit(board.getBoard()[4][2], board.getBoard()[0][6]));    //flytter ned/høyre
        assertTrue(bishop.isPieceLegit(board.getBoard()[1][4], board.getBoard()[5][0]));    //flytter opp/venstre
        assertTrue(bishop.isPieceLegit(board.getBoard()[2][4], board.getBoard()[0][2]));    //flytter ned/venstre 
        assertFalse(bishop.isPieceLegit(board.getBoard()[0][0], board.getBoard()[2][0]));   //flytter vertikalt
        assertFalse(bishop.isPieceLegit(board.getBoard()[3][3], board.getBoard()[3][5]));   //flytter horisontalt
        assertFalse(bishop.isPieceLegit(board.getBoard()[2][1], board.getBoard()[5][3]));   //flytter lenger vertikalt
        assertFalse(bishop.isPieceLegit(board.getBoard()[2][1], board.getBoard()[3][4]));   //flytter lenger horisontalt

        Pawn occupier = new Pawn(board.getBoard()[1][1], false, false);                            //setter bonde i veien

        assertFalse(bishop.isNotOccupied(board.getBoard()[0][0], board.getBoard()[3][3]));  //prøver å flytte gjennom bonden (opp/høyre)
        assertFalse(bishop.isNotOccupied(board.getBoard()[2][0], board.getBoard()[0][2]));  //prøver å flytte gjennom bonden (ned/høyre)
        assertFalse(bishop.isNotOccupied(board.getBoard()[0][2], board.getBoard()[2][0]));  //prøver å flytte gjennom bonden (opp/venstre)
        assertFalse(bishop.isNotOccupied(board.getBoard()[3][3], board.getBoard()[0][0]));  //prøver å flytte gjennom bonden (ned/venstre)
        assertTrue(bishop.isNotOccupied(board.getBoard()[0][1], board.getBoard()[2][3]));   //prøver å flytte ved siden av bonden
        assertTrue(bishop.isNotOccupied(board.getBoard()[3][3], board.getBoard()[1][1]));   //prøver å ta bonden
    }

    @Test
    @DisplayName("Test queen subclass")
    public void testQueenMoves() {
        Board board = new Board(true);
        Piece queen = new Queen(board.getBoard()[3][3], true);
        assertTrue(queen.isPieceLegit(board.getBoard()[3][3], board.getBoard()[3][0]));     //flytter venstre
        assertTrue(queen.isPieceLegit(board.getBoard()[3][3], board.getBoard()[3][6]));     //flytter høyre
        assertTrue(queen.isPieceLegit(board.getBoard()[3][3], board.getBoard()[0][3]));     //flytter ned
        assertTrue(queen.isPieceLegit(board.getBoard()[3][3], board.getBoard()[6][3]));     //flytter opp
        assertTrue(queen.isPieceLegit(board.getBoard()[3][3], board.getBoard()[5][5]));     //flytter opp høyre 
        assertTrue(queen.isPieceLegit(board.getBoard()[3][3], board.getBoard()[4][2]));     //flytter opp venstre 
        assertTrue(queen.isPieceLegit(board.getBoard()[3][3], board.getBoard()[0][0]));     //flytter ned venstre
        assertTrue(queen.isPieceLegit(board.getBoard()[3][3], board.getBoard()[1][5]));     //flytter ned høyre 
        assertFalse(queen.isPieceLegit(board.getBoard()[3][3], board.getBoard()[4][1]));    //prøver å flytte i en L
        assertFalse(queen.isPieceLegit(board.getBoard()[3][3], board.getBoard()[2][5]));    //prøver å flytte i en L
        assertFalse(queen.isPieceLegit(board.getBoard()[3][3], board.getBoard()[1][0]));    //prøver å flytte lenger venstre enn ned
        assertFalse(queen.isPieceLegit(board.getBoard()[3][3], board.getBoard()[0][1]));    //prøver å flytte lenger ned enn venstre 
        assertFalse(queen.isPieceLegit(board.getBoard()[3][3], board.getBoard()[7][4]));    //prøver å flytte lenger opp enn høyre

        Piece occupier = new Pawn(board.getBoard()[1][1], false, false);

        assertFalse(queen.isNotOccupied(board.getBoard()[0][0], board.getBoard()[2][2]));       //prøver å flytte gjennom bonden opp/høyre
        assertFalse(queen.isNotOccupied(board.getBoard()[2][2], board.getBoard()[0][0]));       //prøver å flytte gjennom bonden ned/venstre
        assertFalse(queen.isNotOccupied(board.getBoard()[0][1], board.getBoard()[5][1]));       //prøver å flytte gjennom bonden opp
        assertFalse(queen.isNotOccupied(board.getBoard()[4][1], board.getBoard()[0][1]));       //prøver å flytte gjennom bonden ned
        assertFalse(queen.isNotOccupied(board.getBoard()[1][0], board.getBoard()[1][3]));       //prøver å flytte gjennom bonden høyre
        assertFalse(queen.isNotOccupied(board.getBoard()[1][4], board.getBoard()[1][0]));       //prøver å flytte gjennom bonden venstre
        assertFalse(queen.isNotOccupied(board.getBoard()[2][0], board.getBoard()[0][2]));       //prøver å flytte gjennom bonden ned/høyre
        assertFalse(queen.isNotOccupied(board.getBoard()[0][2], board.getBoard()[2][0]));       //prøver å flytte gjennom bonden ned/venstre

        assertTrue(queen.isNotOccupied(board.getBoard()[0][1], board.getBoard()[1][1]));        //tar bonden vertikalt
        assertTrue(queen.isNotOccupied(board.getBoard()[3][3], board.getBoard()[1][1]));        //tar bonden diagonalt
        assertTrue(queen.isNotOccupied(board.getBoard()[1][4], board.getBoard()[1][1]));        //tar bonden horisontalt
        assertTrue(queen.isNotOccupied(board.getBoard()[2][0], board.getBoard()[2][5]));        //flytter forbi bonden på oversiden
        assertTrue(queen.isNotOccupied(board.getBoard()[6][2], board.getBoard()[0][2]));        //flytter forbi bonden til høyre
        assertTrue(queen.isNotOccupied(board.getBoard()[3][0], board.getBoard()[0][3]));        //flytter forbi bonden diagonalt
        assertTrue(queen.isNotOccupied(board.getBoard()[0][1], board.getBoard()[3][4]));        //flytter forbi bonden diagonalt
    }

    @Test
    @DisplayName("Test king subclass")
    public void testKingMoves() {
        Board board = new Board(true);
        Piece king = new King(board.getBoard()[3][3], true);
        assertTrue(king.isPieceLegit(board.getBoard()[3][3], board.getBoard()[4][4]));      //flytter opp/høyre
        assertTrue(king.isPieceLegit(board.getBoard()[3][3], board.getBoard()[4][3]));      //flytter opp
        assertTrue(king.isPieceLegit(board.getBoard()[3][3], board.getBoard()[4][2]));      //flytter opp/venstre
        assertTrue(king.isPieceLegit(board.getBoard()[3][3], board.getBoard()[3][4]));      //flytter høyre
        assertTrue(king.isPieceLegit(board.getBoard()[3][3], board.getBoard()[3][2]));      //flytter venstre
        assertTrue(king.isPieceLegit(board.getBoard()[3][3], board.getBoard()[2][4]));      //flytter ned høyre
        assertTrue(king.isPieceLegit(board.getBoard()[3][3], board.getBoard()[2][3]));      //flytter ned
        assertTrue(king.isPieceLegit(board.getBoard()[3][3], board.getBoard()[2][2]));      //flytter ned venstre

        Piece occupier = new Pawn(board.getBoard()[1][1], false, false);

        assertTrue(king.isNotOccupied(board.getBoard()[3][3], board.getBoard()[4][4]));     //sjekker at ingenting i veien
        assertTrue(king.isNotOccupied(board.getBoard()[2][2], board.getBoard()[1][1]));     //tar bonden
    }
    
}
