import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Solutions {
    public static void main(String[] args) {
        String fileName;
        if(args.length == 1) { fileName = args[0]; }
        else { fileName = "boards.png"; }
        
        System.out.println("Constructing Graph...");
        var firstBoard = new TriangleBoard(5);
        var boardGraph = new BoardGraph(firstBoard);
        System.out.println("Rendering graph...");
        var boardGraphRenderer = new BoardGraphRenderer(boardGraph);
        var image = boardGraphRenderer.renderImage(2, 200, 10);
        System.out.printf("Writing image to \"%s\"...\n", fileName);
        try {
            ImageIO.write(image, "png", new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Success");
    }
}
