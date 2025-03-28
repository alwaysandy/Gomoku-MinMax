package Game;

import java.util.function.Function;

public class Game {
    private char[][] board;
    private int size;

    public Game(int size) {
        board = new char[size][size];
    }

    public boolean setCell(CellPos pos, char symbol) {
        if (pos.row < 0 || pos.row >= size || pos.col < 0 || pos.col >= size) return false;
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

        horizontal += countInDirection(row, col, 1, 0, size, 10, color);
        horizontal += countInDirection(row, col, -1, 0, -1, 10, color);
        vertical += countInDirection(row, col, 0, 1, 10, size, color);
        vertical += countInDirection(row, col, 0, -1, 10, -1, color);
        leftDiag += countInDirection(row, col, -1, -1, -1, -1, color);
        leftDiag += countInDirection(row, col, 1, 1, size, size, color);
        rightDiag += countInDirection(row, col, -1, 1, -1, size, color);
        rightDiag += countInDirection(row, col, 1, -1, size, -1, color);

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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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

    public boolean isCorrectMove(CellPos pos) {
        return pos.row >= 0 &&
                pos.row < size &&
                pos.col >= 0 &&
                pos.col < size;
    }

    public boolean isCellEmpty(CellPos pos) {
        return board[pos.row][pos.col] == 0;
    }

    public Game copy() {
        Game copy = new Game(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copy.setCell(new CellPos(i, j), board[i][j]);
            }
        }
        return copy;
    }

    public int getSize() {
        return size;
    }
}
