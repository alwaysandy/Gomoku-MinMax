public class Game {
    private char[][] board;
    private int[] lastMove;
    private char turn;

    public Game() {
        board = new char[9][9];
        turn = 'B';
        lastMove = new int[2];
    }

    public boolean makeMove(int row, int col) {
        if (board[row][col] != 0) {
            System.out.println((int) board[row][col]);
            return false;
        }

        board[row][col] = this.turn;
        lastMove[0] = row;
        lastMove[1] = col;
        swapTurns();
        return true;
    }

    private void swapTurns() {
        if (turn == 'W') {
            turn = 'B';
        } else {
            turn = 'W';
        }
    }

    public boolean checkForWin() {
        int row = lastMove[0];
        int col = lastMove[1];
        char color = board[lastMove[0]][lastMove[1]];
        int horizontal = 1;
        int vertical = 1;
        int left_diag = 1;
        int right_diag = 1;

        horizontal += count_in_direction(row, col, 1, 0, 9, 10, color);
        horizontal += count_in_direction(row, col, -1, 0, -1, 10, color);
        vertical += count_in_direction(row, col,0, 1, 10, 9, color);
        vertical += count_in_direction(row, col, 0, -1, 10, -1, color);
        left_diag += count_in_direction(row, col, -1, -1, -1, -1, color);
        left_diag += count_in_direction(row, col, 1, 1, 9, 9, color);
        right_diag += count_in_direction(row, col, -1, 1, -1, 9, color);
        right_diag += count_in_direction(row, col, 1, -1, 9, -1, color);

        return (horizontal == 5 || vertical == 5 || left_diag == 5 || right_diag == 5);
    }

    private int count_in_direction(int start_y, int start_x, int change_x, int change_y, int limit_x, int limit_y, char color) {
        int x = start_x + change_x;
        int y = start_y + change_y;
        int count = 0;
        while (x != limit_x && y != limit_y) {
            if (board[y][x] == color) {
                count++;
            } else {
                break;
            }

            x += change_x;
            y += change_y;
        }

        return count;
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 'W' && board[i][j] != 'B') {
                    boardString.append('.');
                } else {
                    boardString.append(board[i][j]);
                }
            }

            boardString.append('\n');
        }

        return boardString.toString();
    }
}
