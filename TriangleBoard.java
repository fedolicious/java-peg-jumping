import java.util.List;

public class TriangleBoard extends Board {
    final boolean[] pegBoard;
    public TriangleBoard(int sideLength) {
        holes = (sideLength+1)*sideLength/2;
        
        pegBoard = new boolean[holes];
        pegBoard[0] = false;
        for(int i = 1; i < pegBoard.length; i++) {
            pegBoard[i] = true;
        }
        
        pegs = holes-1;
    }
    
    @Override
    List<BoardMove> getPossibleMoves() {
        return null;
    }
    @Override
    void doMove(BoardMove move) {
        pegBoard[move.from] = false;
        pegBoard[move.over] = false;
        pegBoard[move.to] = true;
        pegs--;
    }
    @Override
    boolean isMoveValid(BoardMove move) {
        //math
        return false;
    }
}
