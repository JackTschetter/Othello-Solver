/** Class player, sets up a generalized player for a game of othello.
 * All other Player classes (Random, Minimax, Alphabeta), inherit from player.
 * Created by Jack Tschetter x500 : tsche043
 */

import java.util.*;
import javafx.util.Pair;

public class Player {
    int color = 1;
    public Player(int mycolor) {
        this.color = mycolor;
    }

    public int get_color() {
        return this.color;
    }

    public Pair<Integer, Integer> make_move(OthelloState state) {
        state = state.makeStateCopy(state);
        Pair<Integer, Integer> curr_move = null;
        //legals = actions(state)
        while(curr_move == null) {
            state.display();
            if(this.color == 1)
                System.out.print("White ");
            else
                System.out.print("Black ");
            System.out.println(" to play.");
            //System.out.println("Legal moves are " + (legals.getKey()) + " " + legals.getValue())
            System.out.println("Enter your move as a r c pair:");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] numbers = input.split(" ");
            //System.out.println(numbers[0] + " " + numbers[1]);
            curr_move = new Pair<Integer, Integer>(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
        }
        return curr_move;
    }
}