package Game;

public class Game {
    private char[][] board;
    private int size;
    private int winningLength;

    public Game(int size, int winningLength) {
        this.size = size;
        this.winningLength = winningLength;
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

        horizontal += countInDirection(row, col, 1, 0, color);
        horizontal += countInDirection(row, col, -1, 0, color);
        vertical += countInDirection(row, col, 0, 1, color);
        vertical += countInDirection(row, col, 0, -1, color);
        leftDiag += countInDirection(row, col, -1, -1, color);
        leftDiag += countInDirection(row, col, 1, 1, color);
        rightDiag += countInDirection(row, col, -1, 1, color);
        rightDiag += countInDirection(row, col, 1, -1, color);

        return (horizontal == winningLength ||
                vertical == winningLength ||
                leftDiag == winningLength ||
                rightDiag == winningLength);
    }

    // TODO: move this to CellPos class
    private int countInDirection(int startY, int startX, int changeX, int changeY, char color) {
        int x = startX + changeX;
        int y = startY + changeY;
        int count = 0;
        while (isValidAxis(x) && isValidAxis(y)) {
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
        boardString.append("  ");
        for (int i = 0; i < size; i++) {
            boardString.append(i + " ");
        }
        boardString.append('\n');
        for (int i = 0; i < size; i++) {
            boardString.append(i + " ");
            for (int j = 0; j < size; j++) {
                if (board[i][j] != 'W' && board[i][j] != 'B') {
                    boardString.append(". ");
                } else {
                    boardString.append(board[i][j] + " ");
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
        Game copy = new Game(size, winningLength);
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

    public boolean isValidAxis(int axisValue) {
        return axisValue >= 0 && axisValue < size;
    }

    public boolean isDraw() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public char getCell(CellPos pos) {
        if (!isCorrectMove(pos)) return 0;
        return board[pos.row][pos.col];
    }
}
