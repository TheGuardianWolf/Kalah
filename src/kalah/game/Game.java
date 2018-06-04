package kalah.game;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

public abstract class Game<S> implements IGame {
    private int playerTurn = 0;
    private int numberOfPlayers;

    public Game(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    protected abstract boolean validateInput(int input);

    protected abstract boolean isValidMove(int userInput);

    protected abstract int getScore(int player);

    protected abstract S initialiseState(int turnInput);

    protected abstract Iterator<S> createIterator(S initialState);

    protected abstract TurnResult resolveTurnState(S state);

    protected abstract void beforeTurnIteratorNext(int userInput);

    @Override
    public int parseInput(String input) {
        int retVal =  Integer.parseInt(input) - 1;

        if (validateInput(retVal)) {
            throw new InputMismatchException("This is not a valid input");
        }

        return retVal;
    }

    @Override
    public int nextPlayer() {
        playerTurn = (playerTurn + 1) % numberOfPlayers;
        return playerTurn;
    }

    @Override
    public TurnResult takeTurn(int userInput) {
        if (!isGameOver()) {
            if (!isValidMove(userInput)) {
                return TurnResult.INVALID_MOVE;
            }

            S turnState = initialiseState(userInput);

            Iterator<S> turnIterator = createIterator(turnState);

            while (turnIterator.hasNext()) {
                beforeTurnIteratorNext(userInput);

                turnState = turnIterator.next();

                TurnResult turnResult = resolveTurnState(turnState);

                if (turnResult != TurnResult.NEXT_TURN) {
                    return turnResult;
                }
            }
            return TurnResult.NEXT_TURN;
        }

        return TurnResult.GAME_OVER;
    };

    @Override
    public int getPlayerTurn() {
        return playerTurn;
    }

    @Override
    public List<Integer> getScores() {
        ArrayList<Integer> scores = new ArrayList<>();

        for (int i = 0; i < numberOfPlayers; i++) {
            scores.add(getScore(i));
        }

        return scores;
    }

    @Override
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
