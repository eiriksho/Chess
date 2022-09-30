package chess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;
import java.io.PrintWriter;

public class FileTreater implements FileTreatement {
    public void save(Board board) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File("./src/main/resources/saves/chess_save_file.txt") )) {
            SquareIterator iterator = new SquareIterator(board.getBoard());
            writer.println(board.getTurn());
                while (iterator.hasNext()) {
                    Square square = iterator.next();
                    if(square.getPiece() != null) {
                        writer.println(square.getX());
                        writer.println(square.getY());
                        writer.println(square.getPiece().getType());
                        writer.println(square.getPiece().getColor());
                        writer.println(square.getPiece().getIsMoved());                        
                    }
                }
        }
    }

    public Board load() throws FileNotFoundException  {
        Board temp = new Board(true);
		try (Scanner scanner = new Scanner(new File("./src/main/resources/saves/chess_save_file.txt"))) {
            int n = FileTreater.countLineNumberReader("./src/main/resources/saves/chess_save_file.txt");
            temp.setTurn(scanner.nextBoolean());
            for (int i=0; i < (n-1)/5; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                char piece = scanner.next().charAt(0);
                boolean color = scanner.nextBoolean();
                boolean isMoved = scanner.nextBoolean();
                if (piece == 'P') {
                    temp.getBoard()[y][x].setPiece(new Pawn(temp.getBoard()[y][x], color, isMoved));
                }
                if (piece == 'R') {
                    temp.getBoard()[y][x].setPiece(new Rook(temp.getBoard()[y][x], color));
                }
                if (piece == 'N') {
                    temp.getBoard()[y][x].setPiece(new Knight(temp.getBoard()[y][x], color));
                }
                if (piece == 'B') {
                    temp.getBoard()[y][x].setPiece(new Bishop(temp.getBoard()[y][x], color));
                }
                if (piece == 'Q') {
                    temp.getBoard()[y][x].setPiece(new Queen(temp.getBoard()[y][x], color));
                }
                if (piece == 'K') {
                    temp.getBoard()[y][x].setPiece(new King(temp.getBoard()[y][x], color));
                }
            }
        }
        return temp;
        }

    public static int countLineNumberReader(String fileName) {      //hentet fra:
        File file = new File(fileName);                             //https://mkyong.com/java/how-to-get-the-total-number-of-lines-of-a-file-in-java/
        int lines = 0;
        try (LineNumberReader lnr = new LineNumberReader(new FileReader(file))) {
            while (lnr.readLine() != null) ;
            lines = lnr.getLineNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        FileTreater file = new FileTreater();
        Board save = new Board(true);
        save.getBoard()[0][1].setPiece(new Pawn(save.getBoard()[0][1], true, false));
        save.getBoard()[2][2].setPiece(new Rook(save.getBoard()[2][2], false));
        file.save(save);
        Board load = file.load();
        System.out.println(load.getBoard()[2][2].getPiece());
    }
}