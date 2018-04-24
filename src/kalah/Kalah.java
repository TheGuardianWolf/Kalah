package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

import kalah.components.Board;
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
		Board board = new Board(2, 6, 4);
		Player playerOne = new Player();
		Player playerTwo = new Player();

		while(true) {
			// Replace what's below with your implementation
			io.println("+----+-------+-------+-------+-------+-------+-------+----+");
			io.println("| P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |");
			io.println("|    |-------+-------+-------+-------+-------+-------|    |");
			io.println("|  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |");
			io.println("+----+-------+-------+-------+-------+-------+-------+----+");

			String userInput = io.readFromKeyboard("Player 1's turn - Specify house number or 'q' to quit: ");

			if (userInput.equals("q")) {
				break;
			}

			int houseNumber = -1;
			try {
				houseNumber = Integer.parseInt(userInput);
			}
			catch (NumberFormatException e) {
				houseNumber = -1;
			}

			
		}
	}
}
