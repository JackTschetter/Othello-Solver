/** Class MinimaxPlayer inherits from Player, AlphabetaPlayer will inherit from this class.
 * MinimaxPlayer performs minimax search with a user defined depth limit.
 */

import java.util.ArrayList;
import javafx.util.Pair;
import java.util.Arrays;
import java.lang.Math;

public class MinimaxPlayer extends Player{
    int maxDepth = 2;
    /** Constructor. */
    public MinimaxPlayer(int color, int depth) {
        /** Call players constructor, and return whatever that returns. */
        super(color);
        this.maxDepth = depth;
    }

    public int minimax(OthelloState state, int depth, boolean maxPlayer, int originalPlayer) {
        state = state.makeStateCopy(state);
        if (depth == 0 || state.terminal_test()) {
            /** Max player represents the color. */
            return Heuristics.weightedAverage(state, originalPlayer, maxPlayer);
        }
        if (maxPlayer) {
            int value = (int)Integer.MIN_VALUE; //Negative infinity.
            for (Pair<Integer, Integer> action : state.actions()) {
                OthelloState child = state.result(action);
//                child.current.color *= -1;
                value = Math.max(value, minimax(child, depth - 1, false, originalPlayer));
            }
            return value;
        } else {
            int value = (int)Integer.MAX_VALUE; //Positive infinity.
            for (Pair<Integer, Integer> action : state.actions()) {
                OthelloState child = state.result(action);
                //System.out.println("Inside here: Key: " + action.getKey() + ", Value: " + action.getValue()+", Child: " + child);
//                child.current.color *= -1;
                value = Math.min(value, minimax(child, depth-1, true, originalPlayer));
            }
            return value;
        }
    }


    @Override
    public Pair<Integer, Integer> make_move(OthelloState state) {
        //state =  new OthelloState(state.current, state.other, makeCopy(state.board_array), state.num_skips);
        state = state.makeStateCopy(state);
        Pair<Integer, Integer> bestMove = state.actions().get(0);
        int utilityOfBestMove = (int)Integer.MIN_VALUE;
        for (Pair<Integer, Integer> legalMove : state.actions()) {
            OthelloState copiedState = state.makeStateCopy(state);
            OthelloState child = copiedState.result(legalMove);
//            if (child!=null) {
//                child.current.color *= -1;
//                System.out.println("Testing move = " + legalMove + " current player = " + state.current.color + " passing " + child.current.color);
                int temp = (minimax(child, this.maxDepth , false, this.color));
                if (temp > utilityOfBestMove) {
                    bestMove = legalMove;
                    utilityOfBestMove = temp;
                }
//                System.out.println(color + " Utility for " + legalMove + " = "  + temp);
            }
            //System.out.println(minimax(child, 2, false));
//        System.out.println("\n" + color + " Len: " + state.actions().size() + " Best move: " + bestMove + " Utility: " +  utilityOfBestMove);
        return bestMove; //There are no legal moves;
    }
}