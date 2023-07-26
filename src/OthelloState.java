/** Class othello state, based on the python code Dr. Exley provided in the writeup.
 * This code is effectively identical to the python file othellogame.py.
 * The major difference being I translated it into Java.
 * The bulk of the code for rules, gameplay, etc.. is accomplished in this class.
 * In particular lines 220-227 create players p1, and p2, that can be
 * Human, random, minimax, or alphabeta.
 * Created by Jack Tschetter x500 : tsche043
 */

import java.util.ArrayList;
import javafx.util.Pair;
import java.util.Arrays;

public class OthelloState {
    int WHITE = 1;
    int BLACK = -1;
    int EMPTY = 0;
    int SIZE = 8;
    int[][] board_array = new int[SIZE][SIZE];
    Player other;
    Player current;
    int num_skips;
    Pair<Integer, Integer> SKIP = new Pair<Integer, Integer>(-1, -1);

    /** Constructors*/

    public OthelloState(Player currentplayer, Player otherplayer, int[][] board_array, int num_skips) {
        this.board_array = board_array;
        this.num_skips = num_skips;
        this.current = currentplayer;
        this.other = otherplayer;
    }


    public OthelloState(Player currentplayer, Player otherplayer, int num_skips) {
        this.board_array[3][3] = WHITE;
        this.board_array[4][4] = WHITE;
        this.board_array[3][4] = BLACK;
        this.board_array[4][3] = BLACK;
        this.num_skips = num_skips;
        this.current = currentplayer;
        this.other = otherplayer;
    }

    public Player player() {
        return current;
    }//Close player(state).

    public ArrayList<Pair<Integer, Integer>> actions() {
        ArrayList<Pair<Integer, Integer>> legal_actions = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                OthelloState copiedState = this.makeStateCopy(this);
                if (copiedState.result(new Pair<Integer, Integer>(i, j)) != null) {
                    legal_actions.add(new Pair<Integer, Integer>(i, j));
                }
            }//Close inner for loop
        }//Close outer for loop
        if (legal_actions.size() == 0) {
            legal_actions.add(SKIP);
        }

        return legal_actions;
    }//Close actions(this).

    public int[][] makeCopy(int[][] board_array) {
        int[][] newBoard = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                newBoard[i][j] = board_array[i][j];
        return newBoard;
    }

    public OthelloState result(Pair<Integer, Integer> action) {
        if (action.getKey() == SKIP.getKey() && action.getValue() == SKIP.getValue()) {
            OthelloState newthis = new OthelloState(this.other, this.current, makeCopy(this.board_array), this.num_skips + 1);
            return newthis;
        }

        if (this.board_array[action.getKey()][action.getValue()] != EMPTY) {
            return null;
        }

        int color = this.current.get_color();
        OthelloState newthis = new OthelloState(this.other, this.current, makeCopy(this.board_array), this.num_skips);

        newthis.board_array[action.getKey()][action.getValue()] = color;

        boolean flipped = false;

        ArrayList<Pair<Integer, Integer>> directions = new ArrayList<Pair<Integer, Integer>>(8);
        directions.add(new Pair<Integer, Integer>(-1, -1));
        directions.add(new Pair<Integer, Integer>(-1, 0));
        directions.add(new Pair<Integer, Integer>(-1, 1));
        directions.add(new Pair<Integer, Integer>(0, -1));
        directions.add(new Pair<Integer, Integer>(0, 1));
        directions.add(new Pair<Integer, Integer>(1, -1));
        directions.add(new Pair<Integer, Integer>(1, 0));
        directions.add(new Pair<Integer, Integer>(1, 1));

        for (int k = 0; k < directions.size(); k++) {
            Pair<Integer, Integer> d = directions.get(k);
            int i = 1;
            int count = 0;
            while (i <= SIZE) {
                int x = action.getKey() + i * d.getKey();
                int y = action.getValue() + i * d.getValue();
                if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
                    count = 0;
                    break;
                } else if (newthis.board_array[x][y] == -1 * color) {
                    count += 1;
                } else if (newthis.board_array[x][y] == color) {
                    break;
                } else {
                    count = 0;
                    break;
                }
                i++;
            }//Close while loop.
            if (count > 0) {
                flipped = true;
            }

            for (i = 0; i < count; i++) { //in range(count)
                int x = action.getKey() + (i + 1) * d.getKey();
                int y = action.getValue() + (i + 1) * d.getValue();
                newthis.board_array[x][y] = color;
            }//Close for.
        }//Close for.

        if (flipped) {
            return newthis;
        }//Close if
        else{
            return null;
        }//If no pieces are flipped, it's not a legal move.
    }

    public boolean terminal_test() {

        if (num_skips == 2) {
            return true;
        }

        int empty_count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!(board_array[i][j] == BLACK || board_array[i][j] == WHITE))
                    empty_count += 1;
            }
        }
        if (empty_count == 0) {
            return true;
        }
        return false;
    }

    public void display () {
        System.out.print("  ");
        for(int i = 0; i < (SIZE); i++)
            System.out.print("");
        System.out.println("");
        for(int i = 0; i < (SIZE); i++) {
            System.out.print(i + "");
            for(int j = 0; j < (SIZE); j++) {
                if (this.board_array[j][i] == WHITE) {
                    System.out.print("W");
                }
                else if(this.board_array[j][i] == BLACK) {
                    System.out.print("B");
                }
                else {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }//close display

    public void display_final() {
        int wcount = 0;
        int bcount = 0;
        for (int i = 0; i < (SIZE); i++) {
            for (int j = 0; j < (SIZE); j++) {
                if (this.board_array[i][j] == WHITE) {
                    wcount++;
                } else if (this.board_array[i][j] == BLACK) {
                    bcount++;
                }
            }
        }

        System.out.println("Black: " + (bcount));
        System.out.println("White: " + (wcount));
        if(wcount == 0)
            display();
        if (wcount > bcount) {
            System.out.println("White wins");
        } else if (wcount < bcount) {
            System.out.println("Black wins");
        } else {
            System.out.println("Black wins");
        }
    }

    public OthelloState makeStateCopy(OthelloState state) {
        return new OthelloState(new Player(state.current.color), new Player(state.other.color),makeCopy(state.board_array), state.num_skips);
    }

    public void play_game(Player p1, Player p2) {
        if (p1 == null) {
            p1 = new AlphabetaPlayer(BLACK, 4);
        }

        if (p2 == null) {
            p2 = new RandomPlayer(WHITE);
        }
        for (int i = 0; i < 10; i++) {
            OthelloState s = new OthelloState(p1, p2, 0);
            while (true) {

                Pair<Integer, Integer> action = p1.make_move(s);
                if (!s.actions().contains(action)) {
                    s.display();
                    System.out.println("len = " + s.actions().get(0) + " num skips = " + s.num_skips + " " + action);
                    System.out.println("Illegal move made by Black");
                    System.out.println("White wins!");
                    break;
                }

                s = s.result(action);

                if (s.terminal_test()) {
                    System.out.println("Game Over");
                    s.display_final();
                    break;
                }

                action = p2.make_move(s);
                if (!s.actions().contains(action)) {
                    System.out.println("Illegal move made by White");
                    System.out.println("Black wins!");
                    break;
                }
                s = s.result(action);

                if (s.terminal_test()) {
                    System.out.println("Game Over");
                    s.display_final();
                    break;
                }
            }
        }
    }//Close while loop.
}//Close class.