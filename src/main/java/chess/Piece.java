package chess;

import java.lang.Math;

import javafx.scene.image.Image;

public abstract class Piece {
    private boolean color;
    private boolean isMoved = false;
    private char type;
    private Image image;

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image){
        this.image = image;
    }

    public void setColor(boolean color){
        this.color = color;
    }

    public boolean getColor() {
        return color;
    }

    public void setIsMoved(boolean isMoved) {
        this.isMoved = isMoved;
    }

    public boolean getIsMoved(){
        return this.isMoved;
    }

    public char getType() {
        return this.type;
    }

    public void setType(char s){
        this.type = s;
    }

    public boolean canCheck(Square start, Square end) {     //sjekker at brikken kan bevege seg dit
        if(validMove(start, end) && isPieceLegit(start, end) && isNotOccupied(start, end)){
            return true;
        }
        else{
            return false;
        }
     }

    public boolean canMove(Square start, Square end){       //sjekker om trekket er lovlig, altså at brikken kan bevege seg dit, og spilleren ikke er i sjakk etterpå
        if(canCheck(start, end)){
            Board shadowboard = new Board(start.getBoard());
            if(shadowboard.getBlackKing().getX() == start.getX() && shadowboard.getBlackKing().getY() == start.getY()){
                shadowboard.setBlackKing(shadowboard.getBoard()[end.getY()][end.getX()]);
            }
            else if(shadowboard.getWhiteKing().getX() == start.getX() && shadowboard.getWhiteKing().getY() == start.getY()){
                shadowboard.setWhiteKing(shadowboard.getBoard()[end.getY()][end.getX()]);
            }
            shadowboard.getBoard()[end.getY()][end.getX()].setPiece(start.getPiece());
            shadowboard.getBoard()[start.getY()][start.getX()].setPiece(null);
            if(!isCheck(start.getPiece().getColor(), shadowboard)){
                return true;
            }
        } 
        return false;
    }

    public boolean isCheck(boolean color, Board board){     //sjekker om "color" spilleren er satt i sjakk
        SquareIterator iterator = new SquareIterator(board.getBoard());
        while (iterator.hasNext()) {
            Square start = iterator.next();
            if(start.getPiece() == null);
            else if(!color && start.getPiece().canCheck(start, board.getBlackKing())){
                return true;
            }
            else if(color && start.getPiece().canCheck(start, board.getWhiteKing())){
                return true;
            } 
        }
        return false;
        }

    public boolean isCheckMate(Square square){      //sjekker om en av spillerene har tapt (fargen til brikken i ruten "square")
        SquareIterator iterator = new SquareIterator(square.getBoard().getBoard());
        while (iterator.hasNext()) {
            Square square2 = iterator.next();
            if(square.getPiece().getColor() && 
                square.getBoard().getBlackKing().getPiece().canMove(square.getBoard().getBlackKing(), square2)){
                    return false;
                }
            else if(!square.getPiece().getColor() && 
            square.getBoard().getWhiteKing().getPiece().canMove(square.getBoard().getWhiteKing(), square2)){
                return false;
            }
            else if(square2.getPiece() == null);

            else if(square2.getPiece().getColor() == square.getPiece().getColor());

            else if (square2.getPiece().canMove(square2, square)) {
                return false;
            }
            else{
                SquareIterator iterator2 = new SquareIterator(square.getBoard().getBoard());
                while (iterator2.hasNext()) {
                    Square square1 = iterator2.next();
                    if(square2.getPiece().canMove(square2, square1)){
                        return false;
                    }
                    else if(square1.getPiece() == null);
                    else if(square1.getPiece().getColor() == square.getPiece().getColor());
                    else if(square1.getPiece().canMove(square1, square2)){
                        return false;
                }}}}
        return true;}

    public boolean isStaleMate(Square[][] board, boolean color){    //sjekker om "color" spilleren har noen lovlige trekk
        SquareIterator iterator = new SquareIterator(board);
        while (iterator.hasNext()) {
            Square square = iterator.next();
            if(square.getPiece() == null);
                else if(square.getPiece().getColor() == color);
                else{
                    SquareIterator iterator2 = new SquareIterator(board);
                    while (iterator2.hasNext()) {
                        Square square2 = iterator2.next();
                        if(square.getPiece().canMove(square, square2)){
                            return false;
                    }}}}
        return true;}

    public boolean validMove(Square start, Square end){     //sjekker om trekket gir mening
        if(start.getPiece() == null){
            return false;
        }
        else if(start == end){
            return false;
        }
        else if((end.getPiece() != null) && (end.getPiece().getColor() == start.getPiece().getColor())) {
            return false;
        }
        else {
            return true;
        }

    }
    
    protected abstract boolean isPieceLegit(Square start, Square end);      //sjekker om det er slik brikken beveger seg

    protected abstract boolean isNotOccupied(Square start, Square end);     //sjekker om brikken har klar bane

    public boolean changeToQueen(Square end) {
        return false;
    }
    public boolean castling(Square start, Square end){
        return false;
    }

}
class Pawn extends Piece {
    Pawn(Square location, boolean color, boolean isMoved) {
        setColor(color);
        setType('P');
        setIsMoved(isMoved);
        location.setPiece(this);
        if(!color){
            this.setImage(new Image(getClass().getResourceAsStream("bP.png")));
        }
        else if(color){
            this.setImage(new Image(getClass().getResourceAsStream("wP.png")));
        }
    }

    public boolean changeToQueen(Square end){
        if(end.getY() == 7 || end.getY() == 0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isPieceLegit(Square start, Square end){
        Piece piece = start.getPiece();
        if(piece.getColor()){
            if(end.getY()-start.getY() == 1 && (Math.abs(end.getX()-start.getX())<=1)){
                return true;
            }
            else if(end.getY()-start.getY() == 2 && !this.getIsMoved() && start.getX() == end.getX()){
                return true;
            }
            else{
                return false;
            }
        }
        else if(!piece.getColor()){
            if(end.getY()-start.getY() == -1 && (Math.abs(end.getX()-start.getX())<=1)){
                return true;
            }
            else if(end.getY()-start.getY() == -2 && !this.getIsMoved() && start.getX() == end.getX()){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean isNotOccupied(Square start, Square end){
        int i;
        if(start.getPiece().getColor()){
            i = 1;
        }
        else{
            i = -1;
        }
        if(Math.abs(start.getY()-end.getY()) == 2){
            for(int j = 1; j < 3; j++){
                if(start.getBoard().getBoard()[start.getY()+j*i][start.getX()].getPiece() != null){
                    return false;
                }
            }
            return true;    
        }
        else if(Math.abs(end.getY()-start.getY()) == 1 && Math.abs(start.getX()-end.getX()) == 1){
            if(start.getBoard().getBoard()[end.getY()][end.getX()].getPiece() != null){
                return true;
            }
            else{
                return false;
            }
        }
        else if(Math.abs(end.getY()-start.getY()) == 1 && start.getX() == end.getX()){
            if(start.getBoard().getBoard()[end.getY()][end.getX()].getPiece() == null){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }    
    }
}

class Rook extends Piece {
    public Rook(Square location, boolean color){
        setColor(color);
        setType('R');
        location.setPiece(this);
        if(!color){
            this.setImage(new Image(getClass().getResourceAsStream("bR.png")));
        }
        else if(color){
            this.setImage(new Image(getClass().getResourceAsStream("wR.png")));
        }
    }
    public boolean isPieceLegit(Square start, Square end) {
        if (start.getX() == end.getX() || start.getY() == end.getY()) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isNotOccupied(Square start, Square end) {
        if (start.getX() == end.getX()) {
            int minY = Math.min(start.getY(), end.getY());
            int maxY = Math.max(start.getY(), end.getY());
            for (int i = minY + 1; i < maxY; i++) {
                if (start.getBoard().getBoard()[i][start.getX()].getPiece() != null) {
                    return false;
                }
                
            }
            return true;
        }
        else if (start.getY() == end.getY()) {
            int minX = Math.min(start.getX(), end.getX());
            int maxX = Math.max(start.getX(), end.getX());
            for (int i = minX + 1; i < maxX; i++) {
                if (start.getBoard().getBoard()[start.getY()][i].getPiece() != null) {
                    return false;
                }
            }
            return true;
        }
        return false;
        } 
    }

class Bishop extends Piece {
    private int x_direction;
    private int y_direction;
    Bishop(Square location, boolean color) {
        setColor(color);
        setType('B');
        location.setPiece(this);
        if(!color){
            this.setImage(new Image(getClass().getResourceAsStream("bB.png")));
        }
        else if(color){
            this.setImage(new Image(getClass().getResourceAsStream("wB.png")));
        }
        
    }
    public boolean isPieceLegit(Square start, Square end) {
        int dist_x = Math.abs(start.getX() - end.getX());
        int dist_y = Math.abs(start.getY() - end.getY());
        if (dist_x == dist_y) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isNotOccupied(Square start, Square end) {
        int distance = Math.abs(start.getX() - end.getX());
        if (start.getX() < end.getX()) {x_direction = 1;}
        else {x_direction = -1;}
        if (start.getY() < end.getY()) {y_direction = 1;}
        else {y_direction = -1;}
        for (int i = 1; i < distance; i++) {
            if (start.getBoard().getBoard()[start.getY() + y_direction * i][start.getX() + x_direction * i].getPiece() != null) 
            {return false;} 
        }
        return true;
    }
}

class Knight extends Piece {
    Knight(Square location, boolean color) {
        setColor(color);
        setType('N');
        location.setPiece(this);
        if(!color){
            this.setImage(new Image(getClass().getResourceAsStream("bN.png")));
        }
        else if(color){
            this.setImage(new Image(getClass().getResourceAsStream("wN.png")));
        }
    }
    public boolean isPieceLegit(Square start, Square end) {
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        if (0 < x && 0 < y && x + y == 3) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isNotOccupied(Square start, Square end) {
        return true;
    }
}

class King extends Piece {
    King(Square location, boolean color) {
        setColor(color);
        setType('K');
        location.setPiece(this);
        if(!color){
            this.setImage(new Image(getClass().getResourceAsStream("bK.png")));
        }
        else if(color){
            this.setImage(new Image(getClass().getResourceAsStream("wK.png")));
        }

        if(!color){
            location.getBoard().setBlackKing(location);
        }
        else if(color){
            location.getBoard().setWhiteKing(location);
        }
    }
    public boolean isPieceLegit(Square start, Square end) {
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        if(x == 2 && !this.getIsMoved() && start.getX() == 4){
            return true;
        }
        if (x <= 1 && y <= 1) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean isNotOccupied(Square start, Square end){
        if(start.getX()-end.getX() == 2){
            if(end.getPiece() != null || 
            start.getBoard().getBoard()[start.getY()][start.getX()-1].getPiece() != null || 
            start.getBoard().getBoard()[start.getY()][start.getX()-3].getPiece() != null){
                return false;
            }
        }
        else if(start.getX()-end.getX() == -2){
            if(end.getPiece() != null || 
            start.getBoard().getBoard()[start.getY()][start.getX()+1].getPiece() != null){
                return false;
            }
        }
        return true;
    }

    public boolean castling(Square start, Square end){
        if(start.getPiece().getIsMoved()) return false;
        if(start.getX()-end.getX() == 2 && 
        start.getBoard().getBoard()[start.getY()][0].getPiece() != null &&
        !start.getBoard().getBoard()[start.getY()][0].getPiece().getIsMoved()){
            if(!this.isCheck(this.getColor(), start.getBoard()) &&
            this.canMove(start, end) && 
            this.canMove(start, start.getBoard().getBoard()[start.getY()][start.getX()-1])){
                return true;
            }
        }
        else if(start.getX()-end.getX() == - 2 && 
        start.getBoard().getBoard()[start.getY()][7].getPiece() != null &&
        !start.getBoard().getBoard()[start.getY()][7].getPiece().getIsMoved()){
            if(!this.isCheck(this.getColor(), start.getBoard()) &&
            this.canMove(start, end) && 
            this.canMove(start, start.getBoard().getBoard()[start.getY()][start.getX()+1])){
                return true;
            }
        }
        return false;
    }
}

class Queen extends Piece {
    private int x_direction;
    private int y_direction;
    Queen(Square location, boolean color) {
        setColor(color);
        setType('Q');
        location.setPiece(this);
        if(!color){
            this.setImage(new Image(getClass().getResourceAsStream("bQ.png")));
        }
        else if(color){
            this.setImage(new Image(getClass().getResourceAsStream("wQ.png")));
        }
    }
    public boolean isPieceLegit(Square start, Square end) {
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        if (x == y || x == 0 || y == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isNotOccupied(Square start, Square end) {
        if (start.getX() == end.getX()) {                                                      //vannrett
            int minY = Math.min(start.getY(), end.getY());
            int maxY = Math.max(start.getY(), end.getY());
            for (int i = minY + 1; i < maxY; i++) {
                if (start.getBoard().getBoard()[i][start.getX()].getPiece() != null) {
                    return false;
                }   
            }
        }
        else if (start.getY() == end.getY()) {                                                 //loddrett
            int minX = Math.min(start.getX(), end.getX());
            int maxX = Math.max(start.getX(), end.getX());
            for (int i = minX + 1; i < maxX; i++) {
                if (start.getBoard().getBoard()[start.getY()][i].getPiece() != null) {
                    return false;
                }
            }
        }
        else if (Math.abs(start.getX() - end.getX()) == Math.abs(start.getY() - end.getY())) {  //diagonalt
            int distance = Math.abs(start.getX() - end.getX());
            if (start.getX() < end.getX()) {x_direction = 1;}
            else {x_direction = -1;}
            if (start.getY() < end.getY()) {y_direction = 1;}
            else {y_direction = -1;}
            for (int i = 1; i < distance; i++) {
                if (start.getBoard().getBoard()[start.getY() + y_direction * i][start.getX() + x_direction * i].getPiece() != null) 
                {return false;}
            }      
        }
        return true;
        }
    }