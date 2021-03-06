package kalah.game;

import java.util.List;

public interface Game {
    int parseInput(String input);

    void newGame();

    boolean isGameOver();

    TurnResult takeTurn(int userInput);

    int getPlayerTurn();

    List<Integer> getScores();
}
