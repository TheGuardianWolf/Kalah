package kalah;

import java.security.InvalidParameterException;

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

	public void play(IO io) {
		Game game = new Game(2, 6, 4);

		while (true) {
			io.println(game.boardString());

			String userInput = io.readFromKeyboard(String.format("Player %d's turn - Specify house number or 'q' to quit: ", game.getPlayerTurn()));

			if (userInput.equals("q")) {
				break;
			}

			try {
				game.takeTurn(userInput);
			} catch (InvalidParameterException e) {
				io.println("House is empty");
			} catch (NumberFormatException e) {
				io.println("Not a valid house");
			}
			

			// io.println(result);
		}
	}
}
