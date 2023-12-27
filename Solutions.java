import java.util.Stack;
import java.util.List;

public class Solutions {
    public static void main(String[] args) {
        Board board = new TriangleBoard(5);
        List<BoardMove> possibleMoves = board.getPossibleMoves();
        System.out.println(board);
        
        while(!possibleMoves.isEmpty()) {
            board = board.doMove(possibleMoves.get(0));
            System.out.println(board);
            possibleMoves = board.getPossibleMoves();
        }
    }
    boolean tryMoves() {
        return false;
    }
}
