public class Main {
    public static void main(String[] args) {
        OthelloState t = new OthelloState(new Player(-1), new Player(1), 0);
        t.play_game(null, null);
    }
}