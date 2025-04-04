package Player;

import Game.Game;
import Game.CellPos;
import Utils.Input;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(char[] availableColors) {
        super(availableColors);
    }

    @Override
    protected String initializeName() {
        return Input.readText("Enter your name: ");
    }

    @Override
    protected char initializeColor(char[] availableColors) {
        if (availableColors.length == 1) return availableColors[0];
        String[] colorOptions = Input.makeOptionsFromChars(availableColors);
        int choice = Input.readChoice("Choose your color: ", colorOptions);
        return availableColors[choice - 1];
    }

    @Override
    public void displayTurnIndicator() {
        System.out.println("Player " + this.getName() + " (" + this.getColor() + ")" + "'s turn");
    }

    private CellPos readMove(Game game) {
        int row, col;

        while(true) {
            row = Input.readRow(game.getSize());
            if (!game.isValidAxis(row)) {
                System.out.println("Invalid row, try again");
                continue;
            }

            col = Input.readCol(game.getSize());
            if (!game.isValidAxis(col)) {
                System.out.println("Invalid column, try again");
                continue;
            }

            break;
        }

        return new CellPos(row, col);
    }

    @Override
    public CellPos makeMove(Game game) {
        CellPos pos = readMove(game);
        while (!game.isCellEmpty(pos)) {
            System.out.println("Cell is not empty, try again");
            pos = readMove(game);
        }
        return pos;
    }
}
