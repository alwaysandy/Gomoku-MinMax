package Player;

import Game.Game;
import Game.CellPos;

public class AIPlayer extends Player{
    public AIPlayer(char[] availableColors) {
        super(availableColors);
    }

    @Override
    protected String initializeName() {
        return "BamBot";
    }

    @Override
    public void displayTurnIndicator() {
        System.out.println("AI's turn");
        System.out.println("Calculating...");
    }

    @Override
    protected char initializeColor(char[] availableColors) {
        return availableColors[0];
    }

    @Override
    public CellPos makeMove(Game game) {
        System.out.println("AI is making a move...");
        CellPos bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        char aiColour = this.getColor(); // replaced Andy's toggle with ternary below
        char playerColour = (aiColour == 'B') ? 'W' : 'B';
        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) { // search moves
                CellPos pos = new CellPos(i, j);
                if (!game.isCellEmpty(pos)) continue;
                game.setCell(pos, this.getColor());

                // If game is won return early
                if (game.checkForTermination(pos)) {
                    return pos;
                }

                // AI already made move so maximize / minimize for the opposite colour of the AI
                boolean maximizing = playerColour == 'B';
                int score = miniMax(pos, game, 2, maximizing, playerColour, Integer.MIN_VALUE, Integer.MAX_VALUE);
                game.clearCell(pos);

                // The best score for white would be negative, so flip it from negative to positive when the AI
                // is playing as white
                score = aiColour == 'B' ? score : -score;
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = pos;
                }
            }
        }
        return bestMove;
    }

    private int miniMax(CellPos previousMove, Game game, int depth, boolean isMaximizing, char currentColour, int alpha, int beta) {
        if (game.checkForTermination(previousMove)) {
            char winner = game.getCell(previousMove);
            return winner == 'B' ? 1000000 : -1000000;
        }

        if (game.isDraw()) return 0;
        if (depth == 0) return game.evaluateScore();

        char nextColour = (currentColour == 'B') ? 'W' : 'B';

        if (isMaximizing) {
            int score = Integer.MIN_VALUE;
            for (int i = 0; i < game.getSize(); i++) {
                for (int j = 0; j < game.getSize(); j++) {
                    CellPos pos = new CellPos(i, j);
                    if (!game.isCellEmpty(pos)) continue;
                    game.setCell(pos, currentColour);
                    score = Math.max(score, miniMax(pos, game, depth - 1, false, nextColour, alpha, beta));
                    game.clearCell(pos);
                    if (score > beta) return score;
                    alpha = Math.max(score, alpha);
                }

            }
            return score;
        } else {
            int score = Integer.MAX_VALUE;
            for (int i = 0; i < game.getSize(); i++) {
                for (int j = 0; j < game.getSize(); j++) {
                    CellPos pos = new CellPos(i, j);
                    if (!game.isCellEmpty(pos)) continue;
                    game.setCell(pos, currentColour);
                    score = Math.min(score, miniMax(pos, game, depth - 1, true, nextColour, alpha, beta));
                    game.clearCell(pos);
                    if (score < alpha) return score;
                    beta = Math.min(beta, score);
                }
            }
            return score;
        }
    }
}