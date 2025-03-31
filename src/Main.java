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

        int boardSize = 3;
        int winningLength = 3;

        Game game = new Game(boardSize, winningLength);
        Player[] players = new Player[2];

        char[] availableSymbols = new char[]{ 'B', 'W' };
        System.out.println("=== Player 1 ===");
        players[0] = new HumanPlayer(availableSymbols);

        char[] remainingSymbols = new char[]{ players[0].getOpponentSymbol() };
        if (modeChoice == 1) {
            players[1] = new AIPlayer(remainingSymbols);
        } else if (modeChoice == 2) {
            System.out.println("=== Player 2 ===");
            players[1] = new HumanPlayer(remainingSymbols);
        }

        // Player with Black piece starts first
        int currentPlayerIndex = players[0].getSymbol() == 'B' ? 0 : 1;

        while (true) {
            Player currentPlayer = players[currentPlayerIndex];

            game.displayBoard();
            currentPlayer.displayTurnIndicator();
            CellPos movePos = currentPlayer.makeMove(game);
            game.setCell(movePos, currentPlayer.getSymbol());

            if (game.checkForTermination(movePos)) {
                game.displayBoard();
                System.out.println("Player " + currentPlayer.getName() + " won!");
                break;
            }

            if (game.isDraw()) {
                game.displayBoard();
                System.out.println("Draw!");
                break;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        }
    }
}