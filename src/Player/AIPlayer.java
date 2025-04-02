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
        char playerColour = (aiColour == 'W') ? 'B' : 'W';
        for (int i = 0; i < game.getSize(); i++) {  // search moves
            for (int j = 0; j < game.getSize(); j++) {
                CellPos pos = new CellPos(i, j);
                if (!game.isCellEmpty(pos)) continue;
                Game copy = game.copy();    // attempt the move
                copy.setCell(pos, this.getColor());
                if (copy.checkForTermination(pos)){ // return early
                    return pos;
                }
                char nextColor;
                boolean maximizing;
                if (this.getColor() == 'W') {
                    nextColor = 'B';
                    maximizing = false;
                } else {
                    nextColor = 'W';
                    maximizing = true;
                }

                int score = miniMax(pos, copy, 3, false, nextColor);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = pos;
                }
            }
        }
        return bestMove;
    }

    private int miniMax(CellPos previousMove, Game game, int depth, boolean isMaximizing, char color) {
        if (game.isDraw()) return 0;
        if (game.checkForTermination(previousMove)) {
            if (game.getCell(previousMove) == 'B') {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }

        if (depth == 0) {
            return game.evaluateScore();
        }

        if (isMaximizing) {
            int score = Integer.MIN_VALUE;
            for (int i = 0; i < game.getSize(); i++) {
                for (int j = 0; j < game.getSize(); j++) {
                    CellPos pos = new CellPos(i, j);
                    if (!game.isCellEmpty(pos)) continue;
                    Game copy = game.copy();
                    copy.setCell(pos, color);
                    char nextColor;
                    if (color == 'W') {
                        nextColor = 'B';
                    } else {
                        nextColor = 'W';
                    }
                    score = Math.max(score, miniMax(pos, copy, depth - 1, false, nextColor));
                }
            }

            return score;
        } else {
            int score = Integer.MAX_VALUE;
            for (int i = 0; i < game.getSize(); i++) {
                for (int j = 0; j < game.getSize(); j++) {
                    CellPos pos = new CellPos(i, j);
                    if (!game.isCellEmpty(pos)) continue;
                    Game copy = game.copy();
                    copy.setCell(pos, this.getColor());
                    char nextColor;
                    if (color == 'W') {
                        nextColor = 'B';
                    } else {
                        nextColor = 'W';
                    }
                    score = Math.min(score, miniMax(pos, copy, depth - 1, true, nextColor));
                }
            }

            return score;
        }
    }
}