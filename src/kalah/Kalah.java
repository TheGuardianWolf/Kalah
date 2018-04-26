package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

import kalah.components.Board;
import kalah.components.Game;
import kalah.components.Player;

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
			io.println(game.toString());

			String userInput = io.readFromKeyboard(String.format("Player %d's turn - Specify house number or 'q' to quit: ", game.getPlayerTurn()));

			if (userInput.equals("q")) {
				break;
			}

			game.takeTurn(userInput);

			// io.println(result);
		}
	}
}
