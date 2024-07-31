import java.util.*;

public class Solutions {
    static List<BoardMove> moveSequence = new ArrayList<>();
    public static void main(String[] args) {
        final int BOARD_LEN = 5;
        
        int setSize = TriangleBoard.triangleNumber(BOARD_LEN)-1;
        var sets = new ArrayList<Set<Board>>(setSize);
        for(int i = 0; i < setSize; i++) {
            sets.add(new HashSet<>());
        }
        sets.get(0).add(new TriangleBoard(BOARD_LEN));
        for(int i = 0; i < sets.size()-1; i++) {
            Set<Board> set = sets.get(i);
            Set<Board> nextSet = sets.get(i+1);
            for(var board : set) {
                for(var move : board.getPossibleMoves()) {
                    nextSet.add(board.clone().doMove(move));
                }
            }
        }
        for(var set : sets) {
            System.out.printf("length:%s\n", set.size());
        }
    }
    static boolean tryMoves(Board board) {
        List<BoardMove> possibleMoves = board.getPossibleMoves();
        if(possibleMoves.isEmpty()) {
            if(board.getPegs() == 1) {
                Board solutionBoard = new TriangleBoard(5);
                System.out.println(solutionBoard);
                for(BoardMove move : moveSequence) {
                    solutionBoard.doMove(move);
                    System.out.println(move +"\n"+solutionBoard);
                }
                System.exit(0);
            }
        } else {
            for(BoardMove move : possibleMoves) {
                moveSequence.add(move);
                var clone = board.clone().doMove(move);
                tryMoves(clone);
                moveSequence.remove(move);
            }
        }
        return false;
    }
}
