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

    private CellPos readMove() {

       int row = receiveMove("row");
       int col = receiveMove("col");
        return new CellPos(row, col);
    }


    private int receiveMove(String index){
        boolean valid = false;
        int value = 0;
        while (!valid) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter your " + index + ": ");
                value = input.nextInt();
                if (validateMove(value)) {
                    valid = true;
                }
                else {
                    System.out.println("Please Enter a valid " + index + ": ");
                }
            }catch (Exception e) {
                System.out.println("Please Enter a valid " + index + ": ");
            }
        }
        return value;
    }
    private boolean validateMove(int input){
        return input > -1;
    }

    @Override
    public CellPos makeMove(Game game) {
        CellPos pos = readMove();
        while (!game.isCorrectMove(pos)) {
            System.out.println("Invalid move, try again");
            pos = readMove();
        }
        while (!game.isCellEmpty(pos)) {
            System.out.println("Cell is not empty, try again");
            pos = readMove();
        }
        return pos;
    }
}
