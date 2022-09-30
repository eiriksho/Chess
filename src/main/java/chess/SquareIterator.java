package chess;

import java.util.Iterator;

public class SquareIterator implements Iterator<Square>{

    private Square[][] squareIterator;
    private int xCoord = 0;
    private int yCoord = 0;

    public SquareIterator(Square[][] board){
        squareIterator = board;
    }

    @Override
    public boolean hasNext() {
        if(xCoord < 8 && yCoord < 8)
            return true;
        return false;
    }

    @Override
    public Square next() {
        Square square = squareIterator[yCoord][xCoord];
        if(xCoord == 7){
            xCoord = 0;
            yCoord++;
        }
        else{
            xCoord++;
        }
        return square;
    }
    
}
