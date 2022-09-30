package chess;

import java.io.FileNotFoundException;
import java.util.Collection;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
public class Controller {
    private boolean first = true;
    private Board board = new Board(false);
    private Square coord1;
    private Square coord2;
    private FileTreater fileTreater = new FileTreater();
    
    @FXML
    private Button restart;

    @FXML
    private GridPane grid;

    @FXML
    private Label turnId;

    @FXML
    private Label message;

    public void updateBoard(Board board){
        first = true;
        Collection<Node> imageViews = grid.getChildren();
        int i = 0;
        int j = 0;
        for (Node node : imageViews) {
            Piece piece = board.getBoard()[i][j].getPiece();
            if(board.getBoard()[i][j].getPiece() == null){
                ((ImageView) node).setImage(null);
            }
            else{
            ((ImageView) node).setImage(piece.getImage());
            }
            if(j<7){
                j++;
            }
            else{
                j = 0;
                i++;}
            if(i==8){
                break;}}}
    
    @FXML
    public void restartClicked(MouseEvent event){
        this.board.setTurn(true);
        turnId.setText("Hvit sin tur");
        message.setText("Startet nytt spill");
        this.board = new Board(false);
        updateBoard(this.board);
    }

    @FXML
    public void loadClicked(MouseEvent event) throws FileNotFoundException{
        this.board = fileTreater.load();
        updateBoard(this.board);
        message.setText("Startet lagret spill");
        if(board.getTurn()){
            turnId.setText("Hvit sin tur");
        }
        else{
            turnId.setText("Svart sin tur");
        }
    }

    @FXML
    public void saveClicked(MouseEvent event) throws FileNotFoundException{
        message.setText("Lagret spillet");
        fileTreater.save(this.board);
    }
    
    @FXML
    public void mouseClicked(MouseEvent event) {
        String pos = event.getSource().toString();
        Character tempXPos = pos.charAt(19);
        int y = Character.getNumericValue(tempXPos);
        Character tempYPos = pos.charAt(20);
        int x = Character.getNumericValue(tempYPos);

        if (first) {           
            if(board.getBoard()[y][x].getPiece() == null){
                message.setText("Velg en brikke");}
            else if(board.getBoard()[y][x].getPiece().getColor() == board.getTurn()) {
                message.setText("Brikke er valgt");
                coord1 = board.getBoard()[y][x];
                first = false;          
            }
        }
        else {
            this.coord2 = board.getBoard()[y][x];
            try {
                String tryMove = coord1.getBoard().move(coord1, coord2);
                board.setTurn(!board.getTurn());;
                message.setText(tryMove);
                if(board.getTurn()){
                    turnId.setText("Hvit sin tur"); 
                }
                else{
                    turnId.setText("Svart sin tur");
                }
                updateBoard(board);
                first = true;
                } 
                catch (Exception e) {
                   message.setText(e.getMessage());
                   first = true;
                }
        }
    }     

}