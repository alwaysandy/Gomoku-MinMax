package Player;

import Game.Game;
import Game.CellPos;
import Utils.Input;

import java.util.Scanner;

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

    private CellPos readMove(Scanner scanner) {
        System.out.print("Enter your row: ");
        int row = scanner.nextInt();
        System.out.print("Enter your column: ");
        int col = scanner.nextInt();
        System.out.println();
        return new CellPos(row, col);
    }

    @Override
    public CellPos makeMove(Game game) {
        Scanner scanner = new Scanner(System.in);
        CellPos pos = readMove(scanner);
        while (!game.isCorrectMove(pos)) {
            System.out.println("Invalid move, try again");
            pos = readMove(scanner);
        }
        while (!game.isCellEmpty(pos)) {
            System.out.println("Cell is not empty, try again");
            pos = readMove(scanner);
        }
        return pos;
    }
}
