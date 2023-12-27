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
    private int holeRow(int index) {
        return (int) (-0.5 + Math.sqrt(0.25 + 2*index));
    }
    private int holeCol(int index) {
        int row = holeRow(index);
        return index - row*(row+1)/2;
    }
    @Override
    List<BoardMove> getPossibleMoves() {
        return null;
    }
    @Override
    boolean doMove(BoardMove move) {
        if(!isMoveValid(move)) { return false; }
        pegBoard[move.from] = false;
        pegBoard[move.over] = false;
        pegBoard[move.to] = true;
        pegs--;
        return true;
    }
    @Override
    boolean isMoveValid(BoardMove move) {
        if(
            !(0 <= move.from && move.from < pegBoard.length) ||
            !(0 <= move.over && move.over < pegBoard.length) ||
            !(0 <= move.to && move.to < pegBoard.length)
        ) { return false; }
        
        final int midRow = holeRow(move.over);
        final int rowDiff1 = holeRow(move.from) - midRow;
        final int rowDiff2 = midRow - holeRow(move.to);
        if(rowDiff1 != rowDiff2) { return false; }
        
        final int midCol = holeCol(move.over);
        final int colDiff1 = holeCol(move.from) - midCol;
        final int colDiff2 = midCol - holeCol(move.to);
        if(colDiff1 != colDiff2) { return false; }
    
        switch(rowDiff1) {
        case 1:
            return colDiff1 == 1 || colDiff1 == 0;
        case -1:
            return colDiff1 == -1 || colDiff1 == 0;
        case 0:
            return colDiff1 == 1 || colDiff1 == -1;
        default:
            return false;
        }
    }
}
