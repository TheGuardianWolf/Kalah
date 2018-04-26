package kalah;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

import kalah.components.Game;

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
		Game game = new Game(2, 6, 4);

		while (true) {
			String boardString = game.boardString();

			printLines(io, boardString);

			if (!game.hasTurn(game.getPlayerTurn())) {
                io.println("Game over");
                printLines(io, boardString);
                printLines(io, game.scoreString());
                int winner = game.getHighestScorePlayer();
                if (winner == -1) {
                    io.println("A tie!");
                }
                else {
                    io.println(String.format("Player %d wins!", winner + 1));
                }
                break;
            }

			String userInput = io.readFromKeyboard(String.format("Player P%d's turn - Specify house number or 'q' to quit: ", game.getPlayerTurn() + 1));

			if (userInput.equals("q")) {
				io.println("Game over");
				printLines(io, boardString);
				break;
			}

			try {
				game.takeTurn(userInput);
			} catch (InvalidParameterException e) {
				io.println("House is empty. Move again.");
			} catch (NumberFormatException e) {
				io.println("Not a valid house");
			} catch (RuntimeException e) {
			    io.println("Game over");
            }
		}
	}
}
