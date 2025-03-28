import Player.Player;
import Player.AIPlayer;
import Player.HumanPlayer;
import Game.Game;
import Game.CellPos;
import Utils.Input;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Gumoku!");
        System.out.println("This is a game where you have to connect 5 of your symbols in a row, column, or diagonal.");

        System.out.println();
        int modeChoice = Input.readChoice("Choose a mode: ",
                new String[]{
                        "Single player (vs. AI)",
                        "Multiplayer"
                });

        Game game = new Game(9);
        Player[] players = new Player[2];

        char[] availableSymbols = new char[]{'B', 'W'};
        System.out.println("=== Player 1 ===");
        players[0] = new HumanPlayer(availableSymbols);

        char[] remainingSymbols = new char[]{ players[0].getOpponentSymbol() };
        if (modeChoice == 2) System.out.println("=== Player 2 ===");
        players[1] = modeChoice == 1
                ? new AIPlayer(remainingSymbols)
                : new HumanPlayer(remainingSymbols);

        int currentPlayer = 0;
        while (true) {
            game.displayBoard();
            CellPos movePos = players[currentPlayer].makeMove(game);
            char moveSymbol = players[currentPlayer].getSymbol();
            game.setCell(movePos, moveSymbol);
            boolean isWon = game.checkForWin(movePos);
            if (isWon) {
                System.out.println("Player " + players[currentPlayer].getName() + " won!");
                break;
            }
            currentPlayer = (currentPlayer + 1) % 2;
        }
    }
}