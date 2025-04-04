package Player;

import Game.Game;
import Game.CellPos;

// Used for strategy pattern allowing for switching
// between AI and Human without knowledge of the implementation
public abstract class Player {
    private final String name;
    private final char color;

    protected Player(char[] availableColors) {
        this.name = this.initializeName();
        this.color = this.initializeColor(availableColors);
    }

    abstract protected String initializeName();
    abstract protected char initializeColor(char[] availableColors);
    abstract public CellPos makeMove(Game game);
    abstract public void displayTurnIndicator();

    public String getName() { return name; }
    public char getColor() { return color; }
    public char getOpponentColor() { return color == 'B' ? 'W' : 'B'; }
}
