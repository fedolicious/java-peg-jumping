public class BoardMove {
    int from;
    int over;
    int to;
    public BoardMove(int from, int over, int to) {
        this.from = from;
        this.over = over;
        this.to = to;
    }
    @Override
    public String toString() {
        return "BoardMove{" +
                "from=" + from +
                ", over=" + over +
                ", to=" + to +
                '}';
    }
}
