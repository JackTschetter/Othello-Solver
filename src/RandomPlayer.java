/** Class RandomPlayer inherits from Player
 * Instantiating this creates a RandomPlayer object
 * RandomPlayer object will make moves randomly, by picking a "random" move from all legal (possible) moves.
 * Created by Jack Tschetter x500 : tsche043
 */

import java.util.ArrayList;
import javafx.util.Pair;
import java.util.Arrays;
import java.util.Random;

public class RandomPlayer extends Player {

    /**
     * Constructor.
     */
    public RandomPlayer(int myColor) {
        /** Call players constructor, and return whatever that returns. */
        super(myColor);
    }

    @Override
    public Pair<Integer, Integer> make_move(OthelloState state) {
        state = state.makeStateCopy(state);
        ArrayList<Pair<Integer, Integer>> legalMoves = state.actions();
        int rand = (int)(Math.random() * legalMoves.size());
        for (int i=0; i<legalMoves.size(); i++) {
            if(rand==i) {
                return legalMoves.get(i);
            }
        }
        System.out.println("There was an error.");
        return null; //There are no legal moves;
    }
} //extends