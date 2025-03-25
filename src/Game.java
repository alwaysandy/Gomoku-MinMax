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
        int leftDiag = 1;
        int rightDiag = 1;

        horizontal += countInDirection(row, col, 1, 0, 9, 10, color);
        horizontal += countInDirection(row, col, -1, 0, -1, 10, color);
        vertical += countInDirection(row, col,0, 1, 10, 9, color);
        vertical += countInDirection(row, col, 0, -1, 10, -1, color);
        leftDiag += countInDirection(row, col, -1, -1, -1, -1, color);
        leftDiag += countInDirection(row, col, 1, 1, 9, 9, color);
        rightDiag += countInDirection(row, col, -1, 1, -1, 9, color);
        rightDiag += countInDirection(row, col, 1, -1, 9, -1, color);

        return (horizontal == 5 || vertical == 5 || leftDiag == 5 || rightDiag == 5);
    }

    private int countInDirection(int startY, int startX, int changeX, int changeY, int limitX, int limitY, char color) {
        int x = startX + changeX;
        int y = startY + changeY;
        int count = 0;
        while (x != limitX && y != limitY) {
            if (board[y][x] == color) {
                count++;
            } else {
                break;
            }

            x += changeX;
            y += changeY;
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
