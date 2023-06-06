/**
 * Class AlphabetaPlayer inherits from MinimaxPlayer
 * This class overrides the minimax method in MinimaxPlayer
 * The algorithm is effectively the same as minimax...
 * Except it accepts two integer arguments alpha & beta
 * The function alphaBeta performs minimax search with conditionals.
 * The conditionals "prune" uneccesary branches improving performance greatly over minimax search
 * Created by Jack Tschetter x500 : tsche043
 */

import java.time.chrono.HijrahEra;
import java.util.ArrayList;
import javafx.util.Pair;
import java.util.Arrays;

public class AlphabetaPlayer extends MinimaxPlayer {

    /** Constructor. */
    public AlphabetaPlayer(int color, int maxDepth) {
        super(color, maxDepth);
    }

    @Override
    public int minimax(OthelloState state, int depth, boolean maxPlayer, int firstPlayer) {
        return alphaBeta(state, depth, maxPlayer, firstPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public int alphaBeta(OthelloState state, int depth, boolean maxPlayer, int firstPlayer, int alpha, int beta) {
        state = state.makeStateCopy(state);
//        System.out.println("M(" + depth + ", " + maxPlayer + ", " + firstPlayer + " looks like: ");
//                state.display();
        if (depth == 0 || state.terminal_test()) {
            /** Max player represents the color. */
//            System.out.println("M(" + depth + ", " + maxPlayer + ", " + firstPlayer + ") returning " + Heuristics.weightedAverage(state, firstPlayer));
            return Heuristics.weightedAverage(state, firstPlayer, maxPlayer);
        } if (maxPlayer) {
            int value = (int)Integer.MIN_VALUE; //Negative infinity.
            for (Pair<Integer, Integer> action : state.actions()) {
                OthelloState child = state.result(action);
//                child.current.color *= -1;
//                System.out.println("M(" + depth + ", " + maxPlayer + ", " + firstPlayer + ") evaluating " + action);
                value = Math.max(value, alphaBeta(child, depth - 1, false, firstPlayer, alpha, beta));
                if (value >= beta) {
                    break;
                }
                alpha = Math.max(alpha, value);
            }
            return value;
        } else {
            int value = (int)Integer.MAX_VALUE; //Positive infinity.
            for (Pair<Integer, Integer> action : state.actions()) {
                OthelloState child = state.result(action);
                //System.out.println("Inside here: Key: " + action.getKey() + ", Value: " + action.getValue()+", Child: " + child);
//                child.current.color *= -1;
//                System.out.println("M(" + depth + ", " + maxPlayer + ", " + firstPlayer + ") evaluating " + action);
                value = Math.min(value, alphaBeta(child, depth-1, true, firstPlayer, alpha, beta));
                if (value <= alpha) {
                    break;
                }
                beta = Math.min(beta, value);
            }
            return value;
        }
    }
}