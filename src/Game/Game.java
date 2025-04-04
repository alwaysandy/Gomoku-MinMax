package Game;

public class Game {
    private char[][] board;
    private int size;
    private int winningLength;
    private int moveCount;

    public Game(int size, int winningLength) {
        this.size = size;
        this.winningLength = winningLength;
        board = new char[size][size];
    }

    public boolean setCell(CellPos pos, char color) {
        if (pos.row < 0 || pos.row >= size || pos.col < 0 || pos.col >= size) return false;
        if (board[pos.row][pos.col] != 0) return false;
        board[pos.row][pos.col] = color;
        // Increment move count for draw check
        moveCount++;
        return true;
    }

    public boolean clearCell(CellPos pos) {
        if (board[pos.row][pos.col] == 0) {
            return false;
        }

        board[pos.row][pos.col] = (char) 0;
        // Decrement move count for draw check
        moveCount--;
        return true;
    }

    /*
    Count in all 4 axes (8 directions) to check if the latest move is a winning move
     */
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

    /*
    A helper function to count the number of pieces in a direction.
    Stop when the color is different, out of bounds, or empty.
     */
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

    /*
    Same as countInDirection, but include empty cells to count how many possible moves there are.
     */
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

        // Construct column headers
        boardString.append("  ");
        for (int i = 0; i < size; i++) {
            boardString.append((char)(i + 'a') + " ");
        }

        // Construct board grid with row number
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

    /*
    Make sure the move is within the board
     */
    public boolean isCorrectMove(CellPos pos) {
        return pos.row >= 0 &&
                pos.row < size &&
                pos.col >= 0 &&
                pos.col < size;
    }

    public boolean isCellEmpty(CellPos pos) {
        return board[pos.row][pos.col] == 0;
    }

    public int getSize() {
        return size;
    }

    /*
    Make sure row or col is within the board. Used to check row/col separately
     */
    public boolean isValidAxis(int axisValue) {
        return axisValue >= 0 && axisValue < size;
    }

    /*
    Check if the number of move is equal to the number of all cells
     */
    public boolean isDraw() {
        return moveCount == size * size;
    }

    public char getCell(CellPos pos) {
        if (!isCorrectMove(pos)) return 0;
        return board[pos.row][pos.col];
    }

    public int evaluateScore() {
        int blackScore = getColourScore('B');
        int whiteScore = getColourScore('W');
        return blackScore - whiteScore;
    }

    private int getColourScore(char color) {
        int score = -10000000;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                CellPos curr = new CellPos(row, col);
                if (this.getCell(curr) != color) {
                    continue;
                }

                score = Math.max(score, scoreLine(curr, CellPos.Left(), CellPos.Right(), color));
                score = Math.max(score, scoreLine(curr, CellPos.Up(), CellPos.Down(), color));
                score = Math.max(score, scoreLine(curr, CellPos.UpLeft(), CellPos.DownRight(), color));
                score = Math.max(score, scoreLine(curr, CellPos.UpRight(), CellPos.DownLeft(), color));
            }
        }

        return score;
    }

    private boolean canPlaceFive(CellPos pos, CellPos dirOne, CellPos dirTwo, char color) {
        int dirOneValid = countPossibleInDirection(pos, dirOne, color);
        int dirTwoValid = countPossibleInDirection(pos, dirTwo, color);
        return (dirOneValid + dirTwoValid >= 4);
    }

    private int scoreLine(CellPos curr, CellPos dirOne, CellPos dirTwo, char color) {
        int score = -10000000;
        if (!canPlaceFive(curr, dirOne, dirTwo, color)) {
            return score;
        }
        int dirOneCount = countInDirection(curr, dirOne, color);
        int emptyRow = curr.row + dirOne.row * dirOneCount + dirOne.row;
        int emptyCol = curr.col + dirOne.col * dirOneCount + dirOne.col;
        boolean dirOneOpen = isValidAxis(emptyRow)
                && isValidAxis(emptyCol)
                && this.isCellEmpty(new CellPos(emptyRow, emptyCol));

        int dirTwoCount = countInDirection(curr, dirTwo, color);
        emptyRow = curr.row + dirTwo.row * dirTwoCount + dirTwo.row;
        emptyCol = curr.col + dirTwo.col * dirTwoCount + dirTwo.col;
        boolean dirTwoOpen = isValidAxis(emptyRow)
                && isValidAxis(emptyCol)
                && this.isCellEmpty(new CellPos(emptyRow, emptyCol));

        int openMultiplier = 2;
        if (dirOneCount + dirTwoCount < 5 && (dirOneOpen || dirTwoOpen)) {
            score = Math.max(score, (int) Math.pow(10, dirOneCount + dirTwoCount + 1));
            if (dirOneOpen && dirTwoOpen) {
                score *= openMultiplier;
            }

            int rowScore = 4 - Math.abs(4 - curr.row);
            int colScore = 4 - Math.abs(4 - curr.col);
            score += Math.min(rowScore, colScore);
        }

        return score;
    }
}
