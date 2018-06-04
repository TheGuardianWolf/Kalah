package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

import kalah.display.GamePrinter;
import kalah.display.SEKalahGamePrinter;
import kalah.game.Game;
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
		Game game = new SEKalahGame();
		GamePrinter gamePrinter = new SEKalahGamePrinter(io, (SEKalahGame)game, 'q');

		while (true) {
            gamePrinter.printGame();

            String userInput;

            if (!game.isGameOver()) {
                userInput = gamePrinter.printPlayerPrompt();
            }
            else {
                gamePrinter.printGameOver();
                gamePrinter.printGame();
                gamePrinter.printScores();
                gamePrinter.printWinner();
                break;
            }

			if (userInput.equals("q")) {
                gamePrinter.printGameOver();
                gamePrinter.printGame();
                break;
            }

			TurnResult result = game.takeTurn(game.parseInput(userInput));

            if (result == TurnResult.NEXT_TURN) {
                game.nextPlayer();
            }

            if (result == TurnResult.INVALID_MOVE) {
                gamePrinter.printInvalidMove();
            }
		}
	}
}
