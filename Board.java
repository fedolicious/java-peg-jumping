import java.util.List;

public abstract interface Board {
    public List<BoardMove> getPossibleMoves();
    public Board doMove(BoardMove move);
    public boolean isMoveValid(BoardMove move);
    
    int getHoles();
    int getPegs();
}
