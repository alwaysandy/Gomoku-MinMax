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
        for (int i = 0; i < game.getSize(); i++) {  // search moves
            for (int j = 0; j < game.getSize(); j++) {
                CellPos pos = new CellPos(i, j);
                if (!game.isCellEmpty(pos)) continue;
                game.setCell(pos, this.getColor());
                if (game.checkForTermination(pos)){ // return early
                    return pos;
                }
                int score = miniMax(pos, game, 3, false, playerColour, aiColour, Integer.MIN_VALUE, Integer.MAX_VALUE);
                game.clearCell(pos);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = pos;
                }
            }
        }
        return bestMove;
    }

    private int miniMax(CellPos previousMove, Game game, int depth, boolean isMaximizing, char currentColour, char aiColour, int alpha, int beta) {
        if (game.isDraw()) return 0;
        if (game.checkForTermination(previousMove)) {
            char winner = game.getCell(previousMove);   // switched to ternary, and switched to values instead of just constants for the winner value
            return winner == aiColour ? 1000 : -1000;
        }

        if (depth == 0) {
            return evaluatePos(game, aiColour);
        }

        char nextColour = (currentColour == 'B') ? 'W' : 'B';

        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;
            for (int i = 0; i < game.getSize(); i++) {
                for (int j = 0; j < game.getSize(); j++) {
                    CellPos pos = new CellPos(i, j);
                    if (!game.isCellEmpty(pos)) continue;
                    game.setCell(pos, currentColour);
                    int score = miniMax(pos, game, depth - 1, false, nextColour, aiColour, alpha, beta);
                    game.clearCell(pos);
                    if (score > beta) {
                        return score;
                    }

                    maxScore = Math.max(maxScore, score);
                    alpha = Math.max(score, alpha);
                }

            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int i = 0; i < game.getSize(); i++) {
                for (int j = 0; j < game.getSize(); j++) {
                    CellPos pos = new CellPos(i, j);
                    if (!game.isCellEmpty(pos)) continue;
                    game.setCell(pos, currentColour);
                    int score = miniMax(pos, game, depth - 1, true, nextColour, aiColour, alpha, beta);
                    game.clearCell(pos);
                    if (score < alpha) {
                        return score;
                    }
                    minScore = Math.min(minScore, score);
                    beta = Math.min(beta, score);
                }
            }
            return minScore;
        }
    }
    private int evaluatePos(Game game, char aiColour) { // negation in case the ai is playing the other colour
        int score = game.evaluateScore();
        return (aiColour == 'B') ? score : -score;
    }
}