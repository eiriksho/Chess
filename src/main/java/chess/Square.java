package chess;

public class Square {
    private Board board;
    private Piece piece;
    private int x; //Bokstavene
    private int y; //Tallene

    
    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }
    public Piece getPiece() {
        return piece;
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    
    public Square(int ycoord, int xcoord) {
        this.x = xcoord;
        this.y = ycoord;
        this.piece = null;
    }

    public Square(Square square){
        this.x = square.getX();
        this.y = square.getY();
        this.piece = square.getPiece();
    }
}
