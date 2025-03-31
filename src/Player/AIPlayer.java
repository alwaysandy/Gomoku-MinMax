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
    protected char initializeSymbol(char[] availableSymbols) {
        return availableSymbols[0];
    }

    @Override
    public CellPos makeMove(Game game) {
        System.out.println("AI is making a move...");
        return new CellPos(0,0);
    }
}