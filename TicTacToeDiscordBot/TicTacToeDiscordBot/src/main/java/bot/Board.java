package bot;

import java.util.ArrayList;
import java.util.List;

import static bot.AI.human;

public class Board {
    private CellState[][] board = new CellState[][]{
            {CellState.BLANK, CellState.BLANK, CellState.BLANK},
            {CellState.BLANK, CellState.BLANK, CellState.BLANK},
            {CellState.BLANK, CellState.BLANK, CellState.BLANK}
    };

    public CellState[][] getBoard() {
        return board;
    }

    public void place(Integer[] location, CellState player) {
        board[location[0]][location[1]] = player;
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != CellState.BLANK) {
                    output.append(board[i][j]).append(j + 1 == board[i].length ? "" : "|");
                } else {
                    output.append(" ").append(j + 1 == board[i].length ? "" : "|");
                }
            }
            output.append(i + 1 == board.length ? "" : "\n------\n");
        }
        return output.toString();
    }

    public Integer[] checkLoss() {
        //check columns
        for (int i = 0; i < board.length; i++) {
            int count = 0;
            Integer[] emptyPosition = null;
            for (int j = 0; j < board[i].length; j++) {
                if (board[j][i] != human && board[j][i] == CellState.BLANK) {
                    emptyPosition = new Integer[]{j, i};
                } else if(board[j][i] == human) {
                    count++;
                }
            }
            if(count == 2) {
                return emptyPosition;
            }
        }

        //check rows
        int j=0;
        for (CellState[] cellStates : board) {
            Integer[] emptyPosition = null;
            int i=0;
            int count = 0;
            for (CellState cellState : cellStates) {
                if (cellState != human && cellState == CellState.BLANK) {
                    emptyPosition = new Integer[]{j, i};
                } else if(board[j][i] == human) {
                    count++;
                }
                i++;
            }
            j++;
            if (count == 2) {
                return emptyPosition;
            }
        }

        //check diagonals
        int count = 0;
        Integer[] emptyPosition = null;
        for(int i=0; i<3; i++) {
            if (board[i][i] != human && board[i][i] == CellState.BLANK) {
                emptyPosition = new Integer[]{i, i};
            } else if(board[i][i] == human) {
                count++;
            }
            if(count == 2) {
                return emptyPosition;
            }
        }

        count = 0;
        emptyPosition = null;
        int[] opp = {2, 1, 0};
        for(int i=0; i<3; i++) {
            if (board[i][opp[i]] != human && board[i][opp[i]] == CellState.BLANK) {
                emptyPosition = new Integer[]{i, opp[i]};
            } else if(board[i][opp[i]] == human) {
                count++;
            }
            if(count == 2) {
                return emptyPosition;
            }
        }

        return null;
    }

    public CellState checkWon() {
        CellState checkingPlayer;

        //check columns
        for (int i = 0; i < board.length; i++) {
            checkingPlayer = board[0][i];
            if (checkingPlayer == CellState.BLANK) {
                continue;
            }
            for (int j = 0; j < board[i].length; j++) {
                if (board[j][i] != checkingPlayer) {
                    checkingPlayer = null;
                    break;
                }
            }
            if (checkingPlayer != null) {
                return checkingPlayer;
            }
        }

        //check rows
        for (CellState[] cellStates : board) {
            checkingPlayer = cellStates[0];
            if (checkingPlayer == CellState.BLANK) {
                continue;
            }
            for (CellState cellState : cellStates) {
                if (cellState != checkingPlayer) {
                    checkingPlayer = null;
                    break;
                }
            }
            if (checkingPlayer != null) {
                return checkingPlayer;
            }
        }

        //check diagonals
        checkingPlayer = board[0][0];
        if (board[1][1] == checkingPlayer && board[2][2] == checkingPlayer && checkingPlayer != CellState.BLANK) {
            return checkingPlayer;
        }

        checkingPlayer = board[2][0];
        if (board[1][1] == checkingPlayer && board[0][2] == checkingPlayer && checkingPlayer != CellState.BLANK) {
            return checkingPlayer;
        }

        //check tie
        for (CellState[] cellStates : board) {
            for (CellState cellState : cellStates) {
                if (cellState == CellState.BLANK) {
                    return null;
                }
            }
        }

        return CellState.BLANK;
    }

    public List<Integer[]> getAvailablePositions() {
        ArrayList<Integer[]> availablePositions = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == Board.CellState.BLANK) {
                    availablePositions.add(new Integer[]{i, j});
                }
            }
        }
        return availablePositions;
    }

    public enum CellState {
        X, O, BLANK
    }
}
