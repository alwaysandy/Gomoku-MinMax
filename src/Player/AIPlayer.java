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
        // TODO: implement minimax and return the best move
        return new CellPos(0, 0);
    }
}
