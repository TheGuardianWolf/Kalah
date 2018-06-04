package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.display.ConsoleGame;
import kalah.display.DisplayMockIO;
import kalah.display.KalahGamePrinter;
import kalah.game.KalahGame;
import kalah.game.SEKalahGame;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {
	    KalahGame game = new SEKalahGame();
        DisplayMockIO displayIO = new DisplayMockIO(io);
        new ConsoleGame(new KalahGamePrinter(displayIO, displayIO, game, 'q'), game).runGame();
	}
}
