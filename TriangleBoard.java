import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TriangleBoard implements Board {
    final boolean[] pegBoard;
    private final int holes;
    private final int sideLength;
    private int pegs;
    public TriangleBoard(TriangleBoard board) {
        this.pegBoard = board.pegBoard.clone();
        this.holes = board.holes;
        this.sideLength = board.sideLength;
        this.pegs = board.pegs;
    }
    public TriangleBoard(int sideLength) {
        this.sideLength = sideLength;
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
    private int holeIndex(int row, int col) {
        return row*(row+1)/2 + col;
    }
    @Override public List<BoardMove> getPossibleMoves() {
        final int[] rowOffsets = new int[]{-1,-1, 0, 0, 1, 1};
        final int[] colOffsets = new int[]{-1, 0,-1, 1, 1, 0};
        List<BoardMove> moves = new ArrayList<>();
        for(int i = 0; i < pegBoard.length; i++) {
            if(pegBoard[i] != false) { continue; }
            int row = holeRow(i);
            int col = holeCol(i);
            BoardMove move;
            for(int j = 0; j < rowOffsets.length; j++) {
                move = new BoardMove(
                    holeIndex(row+2*rowOffsets[j],col+2*colOffsets[j]),
                    holeIndex(row+rowOffsets[j],col+colOffsets[j]),
                    i
                );
                if(isMoveValid(move)) { moves.add(move); }
            }
        }
        return moves;
    }
    @Override public Board doMove(BoardMove move) {
        if(!isMoveValid(move)) { return null; }
        TriangleBoard clone = new TriangleBoard(this);
        clone.pegBoard[move.from] = false;
        clone.pegBoard[move.over] = false;
        clone.pegBoard[move.to] = true;
        clone.pegs--;
        return clone;
    }
    @Override public boolean isMoveValid(BoardMove move) {
        if(
            !(0 <= move.from && move.from < pegBoard.length) ||
            !(0 <= move.over && move.over < pegBoard.length) ||
            !(0 <= move.to && move.to < pegBoard.length) ||
            pegBoard[move.from] != true ||
            pegBoard[move.over] != true ||
            pegBoard[move.to] != false
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
    @Override public int getHoles() { return holes; }
    @Override public int getPegs() { return pegs; }
    @Override public String toString() {
        StringBuilder str = new StringBuilder();
        for(int row = 0; row < sideLength; row++) {
            str.append(" ".repeat(sideLength - row - 1));
            for(int col = 0; col <= row; col++) {
                if(pegBoard[holeIndex(row,col)] == true) {
                    str.append("X ");
                } else {
                    str.append("O ");
                }
            }
            str.append("\n");
        }
        return str.toString();
    }
}
