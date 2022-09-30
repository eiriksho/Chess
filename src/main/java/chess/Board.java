package chess;

public class Board {
    private Square[][] board = new Square[8][8];
    private boolean turn = true;
    private Square blackKing;
    private Square whiteKing;

    public Square[][] getBoard() {
        return board;
    }
    public void setTurn(boolean turn) {
        this.turn = turn;
    }
    public boolean getTurn() {
        return turn;
    }
    public void setBlackKing(Square end){
        this.blackKing = end;
    }
    public Square getBlackKing(){
        return blackKing;
    }
    public void setWhiteKing(Square end){
        this.whiteKing = end;
    }
    public Square getWhiteKing(){
        return whiteKing;
    }
    public String move(Square start, Square end) {
        Board shadowboard = new Board(start.getBoard());
        shadowboard.getBoard()[end.getY()][end.getX()].setPiece(start.getPiece());
        shadowboard.getBoard()[start.getY()][start.getX()].setPiece(null);
        if(start == this.getBlackKing()){
            shadowboard.setBlackKing(shadowboard.getBoard()[end.getY()][end.getX()]);
            }
        else if(start == this.getWhiteKing()){
            shadowboard.setWhiteKing(shadowboard.getBoard()[end.getY()][end.getX()]);
        }

        if (!start.getPiece().validMove(start, end)) {
            throw new IllegalArgumentException("Ikke lovlig");
        }
        else if (!start.getPiece().isPieceLegit(start, end)) {
            throw new IllegalArgumentException("Ikke sånn brikken beveger seg");
        }
        else if (!start.getPiece().isNotOccupied(start, end)) {
            throw new IllegalArgumentException("Opptatt!");
        }

        else if (start.getPiece().isCheck(start.getPiece().getColor(), shadowboard)){
            throw new IllegalArgumentException("Kan ikke sette seg selv i sjakk");
        }
        else if (start.getPiece().getType() == 'K' && Math.abs(start.getX()-end.getX()) == 2 && !start.getPiece().castling(start, end)){
            throw new IllegalArgumentException("Ikke mulig med rokade");
        }
        else {
            if (start.getPiece().castling(start, end)) {
                if(start.getX()-end.getX() == 2){
                    start.getBoard().getBoard()[start.getY()][3].setPiece(start.getBoard().getBoard()[start.getY()][0].getPiece());
                    start.getBoard().getBoard()[start.getY()][0].setPiece(null);
                    start.getBoard().getBoard()[start.getY()][3].getPiece().setIsMoved(true);
                }
                else if(start.getX()-end.getX() == -2){
                    start.getBoard().getBoard()[start.getY()][5].setPiece(start.getBoard().getBoard()[start.getY()][7].getPiece());
                    start.getBoard().getBoard()[start.getY()][7].setPiece(null);
                    start.getBoard().getBoard()[start.getY()][5].getPiece().setIsMoved(true);
                }
            }
            if(start == this.getBlackKing()){
                this.setBlackKing(end);
            }
            else if(start == this.getWhiteKing()){
                this.setWhiteKing(end);
            }
            
            start.getPiece().setIsMoved(true);
            end.setPiece(start.getPiece());
            start.setPiece(null);
            if(end.getPiece().changeToQueen(end)){
                end.setPiece(new Queen(end, end.getPiece().getColor()));
            }
            if(end.getPiece().isCheck(!end.getPiece().getColor(), end.getBoard())){
                if(end.getPiece().isCheckMate(end)){
                    return "Sjakkmatt";
                }
                else{
                    return "Sjakk";
                }
            }
            if(end.getPiece().isStaleMate(this.getBoard(), end.getPiece().getColor())){
                return "Patt";
            }
            
            return "Trekket er gjort";
        }}

    public Board(Board board){
        int i = -1;
        for(Square[] start : board.getBoard()){
           i++;
           int j = -1;
            for(Square start2 : start){
                Square start1 = new Square(start2);
                if(start1.getPiece() != null){
                    if(start1.getPiece().getType() == 'K' && start1.getPiece().getColor()){
                        this.whiteKing = start1;
                    }
                    else if(start1.getPiece().getType() == 'K' && !start1.getPiece().getColor()){
                        this.blackKing = start1;
                    }  
                }
                j++;
                this.board[i][j] = start1;
                start1.setBoard(this);
            }}}

    public Board(boolean empty) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {           //i er y,       j er x
                board[i][j] = new Square(i, j);
                board[i][j].setBoard(this);         //i er tallene, j er bokstavene
            }
        }
        if(!empty) {
            for (int i = 0; i < 8; i++) {
                board[1][i].setPiece(new Pawn(board[1][i], true, false));        //lager hvite bønder
            }
            for (int i = 0; i < 8; i++) {
                board[6][i].setPiece(new Pawn(board[6][i], false, false));       //lager sorte bønder
            }
            board[0][0].setPiece(new Rook(board[0][0], true));            //hvite og sorte tårn
            board[0][7].setPiece(new Rook(board[0][7], true));
            board[7][0].setPiece(new Rook(board[7][0], false));
            board[7][7].setPiece(new Rook(board[7][7], false));

            board[0][1].setPiece(new Knight(board[0][1], true));          //hvite og sorte springere
            board[0][6].setPiece(new Knight(board[0][6], true));
            board[7][1].setPiece(new Knight(board[7][1], false));
            board[7][6].setPiece(new Knight(board[7][6], false));

            board[0][2].setPiece(new Bishop(board[0][2], true));          //hvite og sorte løpere
            board[0][5].setPiece(new Bishop(board[0][2], true));
            board[7][2].setPiece(new Bishop(board[7][2], false));
            board[7][5].setPiece(new Bishop(board[7][5], false));

            board[0][3].setPiece(new Queen(board[0][3], true));           //hvite og sorte dronninger
            board[7][3].setPiece(new Queen(board[7][3], false));

            board[0][4].setPiece(new King(board[0][4], true));            //hvite og sorte konger
            board[7][4].setPiece(new King(board[7][4], false));
        }
    }

}

