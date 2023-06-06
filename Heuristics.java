/** Class Heuristics implements my utility function.
 * The utility function is actually a collection of several heuristics.
 * The final utility value of the board is found by assigning different weights to those heuristics.
 * The heuristics used are coin parity, mobility, and corners captured.
 * I used the following as references
 *
 * https://courses.cs.washington.edu/courses/cse573/04au/Project/mini1/RUSSIA/Final_Paper.pdf
 * Pearl, Judea (1984) Heuristics : intelligent search strategies for computer problem solving ISBN 978-0-201-05594-8.
 *
 * Created by Jack Tschetter x500 : tsche043
 */

public class Heuristics {

    static int WHITE = 1;
    static int BLACK = -1;
    static int EMPTY = 0;
    static int SIZE = 8;
    /** Constructor */
    public Heuristics () {

    }

    /** Coin parity. */
    public static int getCoinParity(OthelloState state, int color) {
        /** Max_player is the color that gets passed in. */
        int wcount = 0;
        int bcount = 0;
        int max_player_coins = 0;
        int min_player_coins = 0;
        for (int i = 0; i < (state.SIZE); i++) {
            for (int j = 0; j < (state.SIZE); j++) {
                if (state.board_array[i][j] == WHITE) {
                    wcount++;
                } else if (state.board_array[i][j] == BLACK) {
                    bcount++;
                }
            }
        }
        if (color == WHITE) {
            max_player_coins = wcount;
            min_player_coins = bcount;
        } else {
            max_player_coins = bcount;
            min_player_coins = wcount;
        }
//        return (max_player_coins - min_player_coins);
        return (int) (100 * (max_player_coins-min_player_coins) / (max_player_coins + min_player_coins));
    }

    /** Mobility. */
    public static int getMobility(OthelloState state, int color) {
        int numLegalMovesMax = 0;
        int numLegalMovesMin = 0;
        if (state.current.color == color) {
            numLegalMovesMax = state.actions().size();
            state.current.color *= -1;
            numLegalMovesMin = state.actions().size();
            state.current.color *= -1;
        } else {
            numLegalMovesMin = state.actions().size();
            state.current.color *= -1;
            numLegalMovesMax = state.actions().size();
            state.current.color *= -1;
        }
        return (int) (100 * (numLegalMovesMax-numLegalMovesMin) / (numLegalMovesMax + numLegalMovesMin));
    }

    /** Corners Captured. */
    public static int getCornersCaptured(OthelloState state, int color) {
        int cornersCaptured = 0;
        if (state.board_array[0][0] == color) {
            cornersCaptured++;
        } if (state.board_array[0][state.SIZE-1]== color) {
            cornersCaptured++;
        } if (state.board_array[state.SIZE-1][0] == color) {
            cornersCaptured++;
        } if (state.board_array[state.SIZE-1][state.SIZE-1] == color) {
            cornersCaptured++;
        }
        return cornersCaptured;
    }

    /** Stability. */
    public int getStability(OthelloState state, int color) {
        return -1;
    }

    /** Returns the weighted average of the above functions. */
    public static int weightedAverage(OthelloState state, int color, boolean maxPlayeriscurrent) {
        state = state.makeStateCopy(state);
        int coins = getCoinParity(state, color);
        if (state.terminal_test() && coins > 0 && (state.num_skips != 2 || !maxPlayeriscurrent)) {
            return (int)Integer.MAX_VALUE; //Positive infinity.
        }
        else if (state.terminal_test() && coins < 0 && (state.num_skips != 2 || maxPlayeriscurrent)) {
            return (int)Integer.MIN_VALUE; //Negative infinity.
        }
        int corners = getCornersCaptured(state, color);
        int moves = getMobility(state, color);
        int weightedAverage = (5 * coins) + (1 * corners) + (1 * moves);
        return weightedAverage;
    }
}