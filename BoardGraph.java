import java.util.*;

public class BoardGraph {
    public static class Node {
        final Board data;
        final private ArrayList<Node> children;
        final private ArrayList<Node> parents;
        private Node(Board b) {
            data = b;
            children = new ArrayList<>();
            parents = new ArrayList<>();
        }
        public List<Node> children() {
            return Collections.unmodifiableList(children);
            
        }
        public List<Node> parents() {
            return Collections.unmodifiableList(parents);
        }
        @Override public boolean equals(Object o) {
            if(!(o instanceof Node other)) { return false; }
            return data.equals(other.data);
        }
        @Override public int hashCode() { return data.hashCode(); }
    }
    final private ArrayList<Map<Board, Node>> maps;
    public final Optional<Collection<Node>> geMaxBoardSet() {
        return maps.stream()
            .max(Comparator.comparingInt(Map::size))
            .map(Map::values)
            .map(Collections::unmodifiableCollection);
    }
    public final List<Collection<Node>> nodes() {
        return maps.stream()
            .map(Map::values)
            .map(Collections::unmodifiableCollection)
            .toList();
    }
    
    public BoardGraph(Board firstBoard) {
        final int mapCount = firstBoard.getPegs();
        maps = new ArrayList<>(mapCount);
        for(int i = 0; i < mapCount; i++) {
            maps.add(new HashMap<>());
        }
        
        var firstNode = new Node(firstBoard);
        maps.get(0).put(firstBoard, firstNode);
        
        for(int i = 0; i < maps.size()-1; i++) {
            Map<Board, Node> map = maps.get(i);
            Map<Board, Node> childMap = maps.get(i+1);
            for(var node : map.values()) {
                for(var move : node.data.getPossibleMoves()) {
                    Board childBoard = node.data.clone().doMove(move);
                    Node childNode = childMap.get(childBoard);
                    if(childNode == null) {
                        childNode = new Node(childBoard);
                        childMap.put(childBoard, childNode);
                    }
                    childNode.parents.add(node);
                    node.children.add(childNode);
                }
            }
        }
    }
}
