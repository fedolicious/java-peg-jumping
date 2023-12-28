import java.util.ArrayList;
import java.util.Stack;
import java.util.List;

public class Solutions {
    static List<BoardMove> moveSequence = new ArrayList<>();
    public static void main(String[] args) {
        Board board = new TriangleBoard(5);
        tryMoves(board);
        Stack<List<BoardMove>> stack = new Stack<>();
//        List<BoardMove> possibleMoves = board.getPossibleMoves();
//        System.out.println(board);
//
//        while(!possibleMoves.isEmpty()) {
//            board = board.doMove(possibleMoves.get(0));
//            System.out.println(board);
//            possibleMoves = board.getPossibleMoves();
//        }
    }
    static boolean tryMoves(Board board) {
        List<BoardMove> possibleMoves = board.getPossibleMoves();
        if(possibleMoves.isEmpty()) {
            if(board.getPegs() == 1) {
//                System.out.println("move sequence: " + moveSequence);
//                System.out.println("pegs remaining: " + board.getPegs() + "\n" + board);
                
                Board solutionBoard = new TriangleBoard(5);
                System.out.println(solutionBoard);
                for(BoardMove move : moveSequence) {
                    solutionBoard = solutionBoard.doMove(move);
                    System.out.println(move +"\n"+solutionBoard);
                }
                System.exit(0);
            }
        } else {
            for(BoardMove move : possibleMoves) {
                moveSequence.add(move);
                tryMoves(board.doMove(move));
                moveSequence.remove(move);
            }
        }
        return false;
    }
//    public static v
}
