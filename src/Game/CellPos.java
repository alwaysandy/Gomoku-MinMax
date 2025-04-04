package Game;

// A simple class to represent a position on the board
// with Vector operations
public class CellPos {
    public int row;
    public int col;

    public CellPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public CellPos add(CellPos other) {
        return new CellPos(this.row + other.row, this.col + other.col);
    }

    public CellPos mult(int scalar) {
        return new CellPos(this.row * scalar, this.col * scalar);
    }

    public static CellPos Left() {
        return new CellPos(0, -1);
    }

    public static CellPos Right() {
        return new CellPos(0, 1);
    }

    public static CellPos Up() {
        return new CellPos(-1, 0);
    }

    public static CellPos Down() {
        return new CellPos(1, 0);
    }

    public static CellPos UpLeft() {
        return new CellPos(-1, -1);
    }

    public static CellPos UpRight() {
        return new CellPos(-1, 1);
    }

    public static CellPos DownLeft() {
        return new CellPos(1, -1);
    }

    public static CellPos DownRight() {
        return new CellPos(1, 1);
    }
}
