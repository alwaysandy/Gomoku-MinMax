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

    public boolean setCell(CellPos pos, char color) {
        if (pos.row < 0 || pos.row >= size || pos.col < 0 || pos.col >= size) return false;
        if (board[pos.row][pos.col] != 0) return false;
        board[pos.row][pos.col] = color;
        return true;
    }

    public boolean checkForTermination(CellPos lastMove) {
        char color = board[lastMove.row][lastMove.col];

        int horizontal = 1;
        int vertical = 1;
        int leftDiag = 1;
        int rightDiag = 1;

        horizontal += countInDirection(lastMove, CellPos.Left(), color);
        horizontal += countInDirection(lastMove, CellPos.Right(), color);
        vertical += countInDirection(lastMove, CellPos.Up(), color);
        vertical += countInDirection(lastMove, CellPos.Down(), color);
        leftDiag += countInDirection(lastMove, CellPos.UpLeft(), color);
        leftDiag += countInDirection(lastMove, CellPos.DownRight(), color);
        rightDiag += countInDirection(lastMove, CellPos.UpRight(), color);
        rightDiag += countInDirection(lastMove, CellPos.DownLeft(), color);

        return (horizontal == winningLength ||
                vertical == winningLength ||
                leftDiag == winningLength ||
                rightDiag == winningLength);
    }

    private int countInDirection(CellPos startPos, CellPos direction, char color) {
        CellPos currentPos = startPos.add(direction);
        int count = 0;
        while (isValidAxis(currentPos.row) && isValidAxis(currentPos.col)) {
            if (getCell(currentPos) == color) count++;
            else break;
            currentPos = currentPos.add(direction);
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
