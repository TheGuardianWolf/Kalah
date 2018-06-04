package kalah.display;

import kalah.game.KalahGame;

import java.util.List;

public class KalahGamePrinter implements IGamePrinter {
    IGameInput gameInput;
    IGameOutput gameOutput;
    KalahGame game;
    char quitKey;

    public KalahGamePrinter(IGameInput gameInput, IGameOutput gameOutput, KalahGame game, char quitKey) {
        this.gameInput = gameInput;
        this.gameOutput = gameOutput;
        this.game = game;
        this.quitKey = quitKey;
    }

    @Override
    public void printGame() {
        gameOutput.writeLine("+----+-------+-------+-------+-------+-------+-------+----+");

        StringBuilder s1 = new StringBuilder("| P2 |");
        for (int i = game.getBoard().getNumberOfHouses(1) - 1; i >= 0; i--) {
            s1.append(String.format(" %d[%2d] |", i + 1, game.getBoard().getSeedsInHouse(1, i)));
        }
        s1.append(String.format(" %2d |", game.getBoard().getSeedsInStore(0)));
        gameOutput.writeLine(s1.toString());

        gameOutput.writeLine("|    |-------+-------+-------+-------+-------+-------|    |");

        StringBuilder s2 = new StringBuilder(String.format("| %2d |", game.getBoard().getSeedsInStore(1)));
        for (int i = 0; i < game.getBoard().getNumberOfHouses(0); i++) {
            s2.append(String.format(" %d[%2d] |", i + 1, game.getBoard().getSeedsInHouse(0, i)));
        }
        s2.append(" P1 |");
        gameOutput.writeLine(s2.toString());

        gameOutput.writeLine("+----+-------+-------+-------+-------+-------+-------+----+");
    }

    @Override
    public String printPlayerPrompt() {
        return gameInput.readLine(String.format("Player P%d's turn - Specify house number or '%s' to quit: ", game.getPlayerTurn() + 1, quitKey));
    }

    @Override
    public void printScores() {
        List<Integer> scoreList = game.getScores();
        for (int i = 0; i < scoreList.size(); i++) {
            gameOutput.writeLine(String.format("\tplayer %d:%d", i + 1, scoreList.get(i)));
        }
    }

    @Override
    public void printWinner() {
        List<Integer> scoreList = game.getScores();

        int maxScore = 0;
        int winner = 0;
        for (int i = 0; i < scoreList.size(); i++) {
            int score = scoreList.get(i);
            if (score > maxScore) {
                maxScore = score;
                winner = i + 1;
            }
            else if (score == maxScore) {
                gameOutput.writeLine("A tie!");
                return;
            }
        }

        gameOutput.writeLine(String.format("Player %d wins!", winner));
    }

    @Override
    public void printGameOver() {
        gameOutput.writeLine("Game over");
    }

    @Override
    public void printInvalidMove() {
        gameOutput.writeLine("House is empty. Move again.");
    }
}
