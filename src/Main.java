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

        final int BOARD_SIZE = 9;
        final int WINNING_LENGTH = 5;

        Game game = new Game(BOARD_SIZE, WINNING_LENGTH);
        Player[] players = new Player[2];

        char[] availableColors = new char[]{ 'B', 'W' };
        System.out.println("=== Player 1 ===");
        players[0] = new HumanPlayer(availableColors);

        // Using strategy pattern to allow for different types of players
        char[] remainingColors = new char[]{ players[0].getOpponentColor() };

        if (modeChoice == 1) { // Single player
            players[1] = new AIPlayer(remainingColors);
        } else if (modeChoice == 2) { // Multiplayer
            System.out.println("=== Player 2 ===");
            players[1] = new HumanPlayer(remainingColors);
        }

        // Using an index to keep track of the current player which can be swapped around and called makeMove
        // Player with Black piece starts first
        int currentPlayerIndex = players[0].getColor() == 'B' ? 0 : 1;

        // Game loop
        while (true) {
            Player currentPlayer = players[currentPlayerIndex];

            game.displayBoard();
            currentPlayer.displayTurnIndicator();

            // Read the move from the player (either AI or Human)
            CellPos movePos = currentPlayer.makeMove(game);
            game.setCell(movePos, currentPlayer.getColor());
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            System.out.println("Last move: " + movePos.row + " " + (char)(movePos.col + 'a'));

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