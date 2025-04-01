import Player.Player;
import Player.AIPlayer;
import Player.HumanPlayer;
import Game.Game;
import Game.CellPos;
import Utils.Input;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Gumoku!");
        System.out.println("This is a game where you have to connect 5 of your pieces in a row, column, or diagonal.");

        System.out.println();
        int modeChoice = Input.readChoice("Choose a mode: ",
                new String[]{
                        "Single player (vs. AI)",
                        "Multiplayer"
                });

        int boardSize = 9;
        int winningLength = 5;

        Game game = new Game(boardSize, winningLength);
        Player[] players = new Player[2];

        char[] availableColors = new char[]{ 'B', 'W' };
        System.out.println("=== Player 1 ===");
        players[0] = new HumanPlayer(availableColors);

        char[] remainingColors = new char[]{ players[0].getOpponentColor() };
        if (modeChoice == 1) {
            players[1] = new AIPlayer(remainingColors);
        } else if (modeChoice == 2) {
            System.out.println("=== Player 2 ===");
            players[1] = new HumanPlayer(remainingColors);
        }

        // Player with Black piece starts first
        int currentPlayerIndex = players[0].getColor() == 'B' ? 0 : 1;

        while (true) {
            Player currentPlayer = players[currentPlayerIndex];

            game.displayBoard();
            System.out.println(game.evaluateScore());
            currentPlayer.displayTurnIndicator();
            CellPos movePos = currentPlayer.makeMove(game);
            game.setCell(movePos, currentPlayer.getColor());

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