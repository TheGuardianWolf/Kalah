package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

import kalah.game.PrintableGame;
import kalah.game.SEKalahGame;
import kalah.game.TurnResult;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	private void printLines(IO io, String s) {
		String[] lines = s.split(String.format("%n"));

		for (String line : lines) {
			io.println(line);
		}
	}

	public void play(IO io) {
		PrintableGame game = new SEKalahGame(io, 'q');
		game.newGame();

		while (true) {
            game.printGame();

            String userInput;

            if (!game.isGameOver()) {
                userInput = game.printPlayerPrompt();
            }
            else {
                game.printGameOver();
                game.printGame();
                game.printScores();
                game.printWinner();
                break;
            }

			if (userInput.equals("q")) {
                game.printGameOver();
                game.printGame();
                break;
            }

			TurnResult result = game.takeTurn(game.parseInput(userInput));

            if (result == TurnResult.NEXT_TURN) {
                game.nextPlayer();
            }

            if (result == TurnResult.INVALID_MOVE) {
                game.printInvalidMove();
            }
		}
	}
}
