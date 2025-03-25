package Game;

public class Game {
    private char[][] board;

    public Game() {
        board = new char[9][9];
    }

    public boolean setCell(CellPos pos, char symbol) {
        if (pos.row < 0 || pos.row > 8 || pos.col < 0 || pos.col > 8) return false;
        if (board[pos.row][pos.col] != 0) return false;
        board[pos.row][pos.col] = symbol;
        return true;
    }

    public boolean checkForWin(CellPos lastMove) {
        int row = lastMove.row;
        int col = lastMove.col;
        char color = board[row][col];
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

    // TODO: move this to CellPos class
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

    public void displayBoard() {
        System.out.println(this.toString());
    }
}
