import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BoardGraphRenderer {
    private static class RenderData {
        private static class Position {
            public final int x;
            public final int y;
            public Position(int xa, int ya) {
                x = xa;
                y = ya;
            }
        }
        final private Map<BoardGraph.Node, Position> map;
        final int imageWidth;
        final int imageHeight;
        public RenderData(BoardGraph boardGraph, int xSpacing, int ySpacing) {
            map = new HashMap<>();
            imageWidth = (Board.drawnWidth()+xSpacing) * boardGraph.geMaxBoardSet().map(Collection::size).orElse(0);
            imageHeight = (Board.drawnHeight()+ySpacing) * boardGraph.nodes().size();
            int yOffset = 0;
            for(var nodesByHoles : boardGraph.nodes()) {
                int xOffset = 0;
                for(var node : nodesByHoles) {
                    map.put(node, new Position(xOffset, yOffset));
                    
                    xOffset += Board.drawnWidth() + xSpacing;
                }
                yOffset += Board.drawnHeight() + ySpacing;
            }
        }
        public Position positionOf(BoardGraph.Node node) {
            return map.get(node);
        }
    }
    final private BoardGraph boardGraph;
    public BoardGraphRenderer(BoardGraph bg) {
        boardGraph = bg;
    }
    public BufferedImage renderImage(int xSpacing, int ySpacing) {
        var renderData = new RenderData(boardGraph, xSpacing, ySpacing);
        var bufferedImage = new BufferedImage(
            renderData.imageWidth,
            renderData.imageHeight,
            BufferedImage.TYPE_3BYTE_BGR);
        var graphics = bufferedImage.getGraphics();
        
        graphics.setColor(Color.white);
        for(var nodeList : boardGraph.nodes())
        for(var node : nodeList) {
            var board = node.data;
            var nodePos = renderData.positionOf(node);
            board.draw(graphics, nodePos.x, nodePos.y);
            for(var childNode : node.children()) {
                var childPos = renderData.positionOf(childNode);
                graphics.drawLine(
                    nodePos.x + Board.drawnWidth()/2,
                    nodePos.y + Board.drawnHeight(),
                    childPos.x + Board.drawnWidth()/2,
                    childPos.y);
            }
        }
        return bufferedImage;
    }
}
