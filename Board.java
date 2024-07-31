import java.util.List;

public abstract interface Board {
    Board clone();
    List<BoardMove> getPossibleMoves();
    Board doMove(BoardMove move);
    boolean isMoveValid(BoardMove move);
    int getHoles();
    int getPegs();
}
