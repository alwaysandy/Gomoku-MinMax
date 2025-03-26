package Player;

import Game.Game;
import Game.CellPos;
import Utils.Input;

public class HumanPlayer extends Player{
    public HumanPlayer(char[] availableSymbols) {
        super(availableSymbols);
    }

    @Override
    protected String initializeName() {
        return Input.readText("Enter your name: ");
    }

    @Override
    protected char initializeSymbol(char[] availableSymbols) {
        String[] symbolOptions = Input.makeOptionsFromChars(availableSymbols);
        int choice = Input.readChoice("Choose your symbol: ", symbolOptions);
        return availableSymbols[choice - 1];
    }

    @Override
    public CellPos makeMove(Game game) {
        // TODO read player input and return the move
        return new CellPos(0, 0);
    }
}
