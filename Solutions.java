import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Solutions {
    public static void main(String[] args) {
        var firstBoard = new TriangleBoard(5);
        var boardGraph = new BoardGraph(firstBoard);
        var boardGraphRenderer = new BoardGraphRenderer(boardGraph);
        var image = boardGraphRenderer.renderImage(2, 200);
        
        try {
            ImageIO.write(image, "png", new File("boards.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
