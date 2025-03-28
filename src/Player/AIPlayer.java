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

    // TODO: test
    @Override
    public CellPos makeMove(Game game) {
        CellPos bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                CellPos pos = new CellPos(i, j);
                if (!game.isCellEmpty(pos)) continue;
                Game copy = game.copy();
                copy.setCell(pos, this.getSymbol());
                int score = this.miniMax(copy, this.getOpponentSymbol(), false);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = pos;
                }
            }
        }
        return bestMove;
    }

    private int miniMax(Game game, char symbol, boolean isMax) {
        int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                CellPos pos = new CellPos(i, j);
                if (!game.isCellEmpty(pos)) continue;
                Game copy = game.copy();
                copy.setCell(pos, this.getSymbol());
                int score = this.miniMax(copy, this.getOpponentSymbol(), !isMax);
                if (isMax) {
                    bestScore = Math.max(bestScore, score);
                } else {
                    bestScore = Math.min(bestScore, score);
                }
            }
        }
        return bestScore;
    }
}
