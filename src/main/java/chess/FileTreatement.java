package chess;

import java.io.FileNotFoundException;

public interface FileTreatement {
    
    public void save(Board board) throws FileNotFoundException;
    
    public Board load() throws FileNotFoundException;
}
