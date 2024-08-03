import java.awt.*;
import java.util.List;

public interface Board {
    Board clone();
    
    void draw(Graphics g, int x, int y);
    static int drawnWidth() { return 19; }
    static int drawnHeight() { return 19; }
    
    boolean equals(Object other);
    int hashCode();
    
    List<BoardMove> getPossibleMoves();
    Board doMove(BoardMove move);
    boolean isMoveValid(BoardMove move);
    
    int getHoles();
    int getPegs();
}
