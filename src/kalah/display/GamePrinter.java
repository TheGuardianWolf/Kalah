package kalah.display;

public interface GamePrinter {
    void printGame();

    String printPlayerPrompt();

    void printScores();

    void printWinner();

    void printGameOver();

    void printInvalidMove();
}
