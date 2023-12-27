import java.util.List;

public abstract class Board {
    protected int holes;
    protected int pegs;
    abstract List<BoardMove> getPossibleMoves();
    abstract void doMove(BoardMove move);
    abstract boolean isMoveValid(BoardMove move);
}
