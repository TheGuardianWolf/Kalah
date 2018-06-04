package kalah.game;

import kalah.game.IGame;

public interface PrintableGame extends IGame {
    void printGame();

    String printPlayerPrompt();

    void printScores();

    void printWinner();

    void printGameOver();

    void printInvalidMove();
}
