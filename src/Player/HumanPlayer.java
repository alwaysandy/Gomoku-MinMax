package Player;

import Game.Game;
import Game.CellPos;
import Utils.Input;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(char[] availableSymbols) {
        super(availableSymbols);
    }

    @Override
    protected String initializeName() {
        return Input.readText("Enter your name: ");
    }

    @Override
    protected char initializeSymbol(char[] availableSymbols) {
        if (availableSymbols.length == 1) return availableSymbols[0];
        String[] symbolOptions = Input.makeOptionsFromChars(availableSymbols);
        int choice = Input.readChoice("Choose your symbol: ", symbolOptions);
        return availableSymbols[choice - 1];
    }

    private CellPos readMove(Game game) {
        int row = receiveMove("row", game);
        int col = receiveMove("col", game);
        return new CellPos(row, col);
    }


    private int receiveMove(String axis, Game game) {
        int value;
        while (true) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter your " + axis + ": ");
                value = input.nextInt();
                if (game.isValidAxis(value)) {
                    return value;
                } else {
                    System.out.println("Invalid " + axis + ". Please Enter your " + axis + ": ");
                }
            } catch (Exception e) {
                System.out.println("Please Enter a valid number.");
            }
        }
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
