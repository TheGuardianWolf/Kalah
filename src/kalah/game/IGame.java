package kalah.game;

import java.util.List;

public interface IGame {
    int parseInput(String input);

    void newGame();

    boolean isGameOver();

    int nextPlayer();

    TurnResult takeTurn(int userInput);

    int getPlayerTurn();

    int getNumberOfPlayers();

    List<Integer> getScores();
}
