import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Solutions {
    static List<BoardMove> moveSequence = new ArrayList<>();
    public static void main(String[] args) {
        final int BOARD_LEN = 5;
        
        int setSize = TriangleBoard.triangleNumber(BOARD_LEN)-1;
        var sets = new ArrayList<Set<TriangleBoard>>(setSize);
        for(int i = 0; i < setSize; i++) {
            sets.add(new HashSet<>());
        }
        
        sets.get(0).add(new TriangleBoard(BOARD_LEN));
        for(int i = 0; i < sets.size()-1; i++) {
            var set = sets.get(i);
            var nextSet = sets.get(i+1);
            for(var board : set) {
                for(var move : board.getPossibleMoves()) {
                    nextSet.add(board.clone().doMove(move));
                }
            }
        }
        
        int maxSize = -1;
        for(var set : sets) {
            maxSize = Math.max(maxSize, set.size());
            System.out.printf("length:%s\n", set.size());
        }
        assert maxSize != -1;
        
        final int VERT_SPACING = 50;
        var bufferedImage = new BufferedImage(
            (Board.drawnWidth()+1)*maxSize + 1,
            (Board.drawnHeight()+VERT_SPACING)*setSize,
        BufferedImage.TYPE_3BYTE_BGR);
        var graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.white);
        int yOffset = VERT_SPACING/2;
        for(var set : sets) {
            int xOffset = 1;
            for(var board : set) {
                board.draw(graphics, xOffset, yOffset);
                xOffset += Board.drawnWidth()+1;
            }
            yOffset += VERT_SPACING;
        }
        try {
            ImageIO.write(bufferedImage, "png", new File("boards.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
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
