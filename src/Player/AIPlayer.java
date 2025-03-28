package Player;

import Game.Game;
import Game.CellPos;

public class AIPlayer extends Player{
    public AIPlayer(char[] availableSymbols) {
        super(availableSymbols);
    }

    @Override
    protected String initializeName() {
        return "BamBot";
    }

    @Override
    protected char initializeSymbol(char[] availableSymbols) {
        return availableSymbols[0];
    }

    @Override
    public CellPos makeMove(Game game) {
        System.out.println("AI is making a move...");
        CellPos bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                CellPos pos = new CellPos(i, j);
                if (!game.isCellEmpty(pos)) continue;
                Game copy = game.copy();
                copy.setCell(pos, this.getSymbol());
                int score = miniMax(pos, copy, 0, false);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = pos;
                }
            }
        }
        return bestMove;
    }

    private int miniMax(CellPos previousMove, Game game, int depth, boolean isMaximizing) {
        if (game.checkForWin(previousMove)) {
            return !isMaximizing ? 10 - depth : depth - 10;
        }

        if (game.isDraw()) return 0;

        int finalScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                CellPos pos = new CellPos(i, j);
                if (!game.isCellEmpty(pos)) continue;
                Game copy = game.copy();
                copy.setCell(pos, this.getSymbol());
                int score = miniMax(pos, copy, depth + 1, false);
                finalScore = isMaximizing
                        ? Math.max(finalScore, score)
                        : Math.min(finalScore, score);
            }
        }
        return finalScore;
    }
}