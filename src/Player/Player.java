package Player;

import Game.Game;
import Game.CellPos;

public abstract class Player {
    private String name;
    private char symbol;

    protected Player(char[] availableSymbols) {
        this.name = this.initializeName();
        this.symbol = this.initializeSymbol(availableSymbols);
    }

    abstract protected String initializeName();
    abstract protected char initializeSymbol(char[] availableSymbols);
    abstract public CellPos makeMove(Game game);

    public String getName() { return name; }
    public char getSymbol() { return symbol; }
    public char getOpponentSymbol() { return symbol == 'B' ? 'W' : 'B'; }
}
