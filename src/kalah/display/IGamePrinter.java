package kalah.display;

public interface IGamePrinter {
    void printGame();

    String printPlayerPrompt();

    void printScores();

    void printWinner();

    void printGameOver();

    void printInvalidMove();
}
