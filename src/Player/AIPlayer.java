package Player;

import Game.Game;
import Game.CellPos;

public class AIPlayer extends Player{
    public AIPlayer(char[] availableSymbols) {
        super(availableSymbols);
    }

    @Override
    protected String initializeName() {
        return "BamBot";
    }

    @Override
    public void displayTurnIndicator() {
        System.out.println("AI's turn");
        System.out.println("Calculating...");
    }

    @Override
    protected char initializeSymbol(char[] availableSymbols) {
        return availableSymbols[0];
    }

    @Override
    public CellPos makeMove(Game game) {
        return new CellPos(0,0);
    }
}