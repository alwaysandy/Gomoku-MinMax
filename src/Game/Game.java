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

    private int countPossibleInDirection(CellPos startPos, CellPos direction, char color) {
        CellPos currentPos = startPos.add(direction);
        int count = 0;
        while (isValidAxis(currentPos.row) && isValidAxis(currentPos.col)) {
            if (getCell(currentPos) != 0 && getCell(currentPos) != color) break;
            currentPos = currentPos.add(direction);
            count += 1;
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

    public int evaluateScore() {
        int blackScore = getBestScore('B');
        int whiteScore = getBestScore('W');
        return blackScore - whiteScore;
    }

    private int getBestScore(char color) {
        int openMultiplier = 2;
        int score = 1;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                CellPos curr = new CellPos(row, col);
                if (this.getCell(curr) != color) {
                    continue;
                }

                int valid_left = countPossibleInDirection(curr, CellPos.Left(), color);
                int valid_right = countPossibleInDirection(curr, CellPos.Right(), color);

                if (valid_left + valid_right >= 4) {
                    int left = countInDirection(curr, CellPos.Left(), color);
                    boolean left_open = isValidAxis(col - left - 1) &&
                            this.isCellEmpty(new CellPos(row, col - left - 1));

                    int right = countInDirection(curr, CellPos.Right(), color);
                    boolean right_open = isValidAxis(col + right + 1) &&
                            this.isCellEmpty(new CellPos(row, col + right + 1));

                    if (left + right == 4) {
                        return 1000;
                    }


                    if (left + right < 5 && (left_open || right_open)) {
                        score = Math.max(score, (int) Math.pow(4, left + right + 1));
                        if (left_open && right_open) {
                            score *= openMultiplier;
                        }
                    }
                }
                int valid_up = countPossibleInDirection(curr, CellPos.Up(), color);
                int valid_down = countPossibleInDirection(curr, CellPos.Down(), color);
                if (valid_up + valid_down >= 4) {
                    int up = countInDirection(curr, CellPos.Up(), color);
                    boolean up_open = isValidAxis(row - up - 1) &&
                            this.isCellEmpty(new CellPos(row - up - 1, col));

                    int down = countInDirection(curr, CellPos.Down(), color);
                    boolean down_open = isValidAxis(row + down + 1) &&
                            this.isCellEmpty(new CellPos(row + down + 1, col));

                    if (up + down == 4) {
                        return 1000;
                    }

                    if (up + down < 5 && (up_open || down_open)) {
                        score = Math.max(score, (int) Math.pow(4, up + down + 1));
                        if (up_open && down_open) {
                            score *= openMultiplier;
                        }
                    }
                }

                int valid_upLeft = countPossibleInDirection(curr, CellPos.UpLeft(), color);
                int valid_downRight = countPossibleInDirection(curr, CellPos.DownRight(), color);

                if (valid_upLeft + valid_downRight >= 4) {
                    int upLeft = countInDirection(curr, CellPos.UpLeft(), color);
                    boolean upLeft_open = isValidAxis(row - upLeft - 1) && isValidAxis(col - upLeft - 1) &&
                            this.isCellEmpty(new CellPos(row - upLeft - 1, col - upLeft - 1));

                    int downRight = countInDirection(curr, CellPos.DownRight(), color);
                    boolean downRight_open = isValidAxis(row + downRight + 1) && isValidAxis(col + downRight + 1) &&
                            this.isCellEmpty(new CellPos(row + downRight + 1, col + downRight + 1));

                    if (upLeft + downRight == 4) {
                        return 1000;
                    }

                    if (upLeft + downRight < 5 && (upLeft_open || downRight_open)) {
                        score = Math.max(score, (int) Math.pow(4, upLeft + downRight + 1));
                        if (upLeft_open && downRight_open) {
                            score *= openMultiplier;
                        }
                    }
                }

                int valid_upRight = countPossibleInDirection(curr, CellPos.UpRight(), color);
                int valid_downLeft = countPossibleInDirection(curr, CellPos.DownLeft(), color);

                if (valid_upRight + valid_downLeft >= 4) {
                    int upRight = countInDirection(curr, CellPos.UpRight(), color);
                    boolean upRight_open = isValidAxis(row - upRight - 1) && isValidAxis(col + upRight + 1) &&
                            this.isCellEmpty(new CellPos(row - upRight - 1, col + upRight + 1));

                    int downLeft = countInDirection(curr, CellPos.DownLeft(), color);
                    boolean downLeft_open = isValidAxis(row + downLeft + 1) && isValidAxis(col - downLeft - 1) &&
                            this.isCellEmpty(new CellPos(row + downLeft + 1, col - downLeft - 1));

                    if (upRight + downLeft == 4) {
                        return 1000;
                    }

                    if (upRight + downLeft < 5 && (upRight_open || downLeft_open)) {
                        score = Math.max(score, (int) Math.pow(4, upRight + downLeft + 1));
                        if (upRight_open && downLeft_open) {
                            score *= openMultiplier;
                        }
                    }
                }
            }
        }

        return score;
    }
}
