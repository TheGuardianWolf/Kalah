package kalah.game;

import com.qualitascorpus.testsupport.IO;

import java.util.List;

public class SEKalahGame extends StandardKalahGame implements PrintableGame {
    private IO io;
    private char quitKey;

    public SEKalahGame(IO io, char quitKey) {
        super(2, 6, 4);
        this.io = io;
        this.quitKey = quitKey;
    }

    @Override
    public void printGame() {
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");

        StringBuilder s1 = new StringBuilder("| P2 |");
        for (int i = getBoard().getNumberOfHouses(1) - 1; i >= 0; i--) {
            s1.append(String.format(" %d[%2d] |", i + 1, getBoard().getSeedsInHouse(1, i)));
        }
        s1.append(String.format(" %2d |", getBoard().getSeedsInStore(0)));
        io.println(s1.toString());

        io.println("|    |-------+-------+-------+-------+-------+-------|    |");

        StringBuilder s2 = new StringBuilder(String.format("| %2d |", getBoard().getSeedsInStore(1)));
        for (int i = 0; i < getBoard().getNumberOfHouses(0); i++) {
            s2.append(String.format(" %d[%2d] |", i + 1, getBoard().getSeedsInHouse(0, i)));
        }
        s2.append(" P1 |");
        io.println(s2.toString());

        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }

    @Override
    public String printPlayerPrompt() {
        return io.readFromKeyboard(String.format("Player P%d's turn - Specify house number or '%s' to quit: ", getPlayerTurn() + 1, quitKey));
    }

    @Override
    public void printScores() {
        List<Integer> scoreList = getScores();
        for (int i = 0; i < scoreList.size(); i++) {
            io.println(String.format("\tplayer %d:%d", i + 1, scoreList.get(i)));
        }
    }

    @Override
    public void printWinner() {
        List<Integer> scoreList = getScores();

        int maxScore = 0;
        int winner = 0;
        for (int i = 0; i < scoreList.size(); i++) {
            int score = scoreList.get(i);
            if (score > maxScore) {
                maxScore = score;
                winner = i + 1;
            }
            else if (score == maxScore) {
                io.println("A tie!");
                return;
            }
        }

        io.println(String.format("Player %d wins!", winner));
    }

    @Override
    public void printGameOver() {
        io.println("Game over");
    }

    @Override
    public void printInvalidMove() {
        io.println("House is empty. Move again.");
    }
}
