import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class BoardGraphRenderer {
    private static class RenderData {
        private static class Position {
            public int x;
            public int y;
            public Position(int xa, int ya) {
                x = xa;
                y = ya;
            }
        }
        final private BoardGraph boardGraph;
        final private Map<BoardGraph.Node, Position> map;
        final int xSpacing;
        final int ySpacing;
        final int imageWidth;
        final int imageHeight;
        public RenderData(BoardGraph bg, int xSpacing_, int ySpacing_) {
            boardGraph = bg;
            map = new HashMap<>();
            xSpacing = xSpacing_;
            ySpacing = ySpacing_;
            imageWidth = (Board.drawnWidth()+xSpacing) * bg.geMaxBoardSet().map(Collection::size).orElse(0);
            imageHeight = (Board.drawnHeight()+ySpacing) * bg.nodes().size();
            int yOffset = 0;
            for(var nodesByHoles : bg.nodes()) {
                int remainingNodes = nodesByHoles.size();
                int xOffset = imageWidth/(nodesByHoles.size()+1);
                for(var node : nodesByHoles) {
                    map.put(node, new Position(xOffset, yOffset));
                    xOffset += (imageWidth-xOffset)/remainingNodes;
                    remainingNodes--;
                }
                yOffset += Board.drawnHeight() + ySpacing;
            }
        }
        private void forceDirect() {
            var allNodes = boardGraph.nodes().stream()
                .map(ArrayList::new)
                .toList();
            for(var nodeList : allNodes) {
                //move nodes based on average of parent/child x
                for(var node : nodeList) {
                    long avg = 0;
                    for(var child : node.children()) {
                        avg += positionOf(child).x;
                    }
                    for(var parent : node.parents()) {
                        avg += positionOf(parent).x;
                    }
                    avg /= node.children().size()+node.parents().size();
                    positionOf(node).x = (int) avg;
                }
                //resolve collisions by moving intersecting nodes right
                nodeList.sort(Comparator.comparingInt(x -> positionOf(x).x));
                for(int i = 0; i < nodeList.size()-1; i++) {
                    var node = nodeList.get(i);
                    var nextNode = nodeList.get(i+1);
                    int minNextNodePos = positionOf(node).x + Board.drawnWidth() + xSpacing;
                    if(positionOf(nextNode).x < minNextNodePos) {
                        positionOf(nextNode).x = minNextNodePos;
                    }
                }
                //resolve collisions by moving intersecting nodes left
                int distanceOverRightEdge = positionOf(nodeList.get(nodeList.size()-1)).x - (imageWidth - Board.drawnWidth());
                if(distanceOverRightEdge > 0) {
                    positionOf(nodeList.get(nodeList.size()-1)).x -= distanceOverRightEdge;
                }
                for(int i = nodeList.size()-1; i > 0; i--) {
                    var node = nodeList.get(i);
                    var prevNode = nodeList.get(i-1);
                    int maxPrevNodePos = positionOf(node).x - Board.drawnWidth() - xSpacing;
                    if(positionOf(prevNode).x > maxPrevNodePos) {
                        positionOf(prevNode).x = maxPrevNodePos;
                    }
                }
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
    public BufferedImage renderImage(int xSpacing, int ySpacing, int forceDirectIterations) {
        var renderData = new RenderData(boardGraph, xSpacing, ySpacing);
        for(int i = 0; i < forceDirectIterations; i++) {
            renderData.forceDirect();
        }
        var bufferedImage = new BufferedImage(
            renderData.imageWidth,
            renderData.imageHeight,
            BufferedImage.TYPE_3BYTE_BGR);
        var graphics = bufferedImage.createGraphics();
        
        graphics.setColor(Color.white);
        graphics.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        for(var nodeList : boardGraph.nodes())
        for(var node : nodeList) {
            var board = node.data;
            var nodePos = renderData.positionOf(node);
            board.draw(graphics, nodePos.x, nodePos.y);
            for(var childNode : node.children()) {
                var childPos = renderData.positionOf(childNode);
                graphics.drawLine(
                    nodePos.x + Board.drawnWidth()/2,
                    nodePos.y + Board.drawnHeight() + 1,
                    childPos.x + Board.drawnWidth()/2,
                    childPos.y - 1);
            }
        }
        return bufferedImage;
    }
}
