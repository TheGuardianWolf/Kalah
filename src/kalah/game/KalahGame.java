package kalah.game;

import kalah.board.KalahBoard;

import java.util.*;

public class KalahGame implements Game {
    private int playerTurn = 0;
    private int numberOfPlayers;
    private int startingHouses;
    private int startingSeeds;
    private KalahBoard board;

    public KalahGame(int numberOfPlayers, int startingHouses, int startingSeeds) {
        this.numberOfPlayers = numberOfPlayers;
        this.startingHouses = startingHouses;
        this.startingSeeds = startingSeeds;

        newGame();
    }

    @Override
    public int parseInput(String input) {
        int retVal =  Integer.parseInt(input) - 1;

        if (retVal < 0 || retVal >= board.getNumberOfHouses(playerTurn)) {
            throw new InputMismatchException("This is not a valid player house");
        }

        return retVal;
    }

    @Override
    public void newGame() {
        board = new KalahBoard(numberOfPlayers, startingHouses, startingSeeds);
    }

    @Override
    public boolean isGameOver() {
        return !playerHasSeeds();
    }

    @Override
    public TurnResult takeTurn(int userInput) {
        if (!isGameOver()) {
            int houseNumber = userInput;

            int seedsToSow = board.getSeedsInHouse(playerTurn, houseNumber);

            if (seedsToSow == 0) {
                return TurnResult.INVALID_MOVE;
            }

            SowState sowState = new SowState(
                    playerTurn,
                    houseNumber + 1,
                    board.getSeedsInHouseOpposite(playerTurn, houseNumber + 1),
                    board.getSeedsInHouse(playerTurn, houseNumber + 1),
                    seedsToSow
            );

            SowIterator sowIterator = new SowIterator(sowState);

            while (sowIterator.hasNext()) {
                board.takeSeedsInHouse(playerTurn, houseNumber, 1);

                sowState = sowIterator.next();

                TurnResult turnResult = resolveTurnState(sowState);

                if (turnResult != TurnResult.NEXT_TURN) {
                    return turnResult;
                }
            }

            return TurnResult.NEXT_TURN;
        }
        return TurnResult.GAME_OVER;
    }

    public TurnResult resolveTurnState(SowState sowState) {
        if (captureCondition(sowState)) {
            capture(sowState.sowingHouseOwner, sowState.sowingHouseNumber);
            return TurnResult.NEXT_TURN;
        }

        if (extraTurnCondition(sowState)) {
            return TurnResult.EXTRA_TURN;
        }
        return TurnResult.NEXT_TURN;
    }

    @Override
    public int getPlayerTurn() {
        return playerTurn;
    }

    @Override
    public List<Integer> getScores() {
        ArrayList<Integer> scores = new ArrayList<Integer>();

        for (int i = 0; i < numberOfPlayers; i++) {
            Integer score = 0;
            for (int j = 0; j < board.getNumberOfHouses(i); j++) {
                score += board.getSeedsInHouse(i, j);
            }
            score += board.getSeedsInStore(i);
            scores.add(score);
        }

        return scores;
    }

    public KalahBoard getBoard() {
        return board;
    }

    private boolean playerHasSeeds() {
        for (int i = 0; i < board.getNumberOfHouses(playerTurn); i++) {
            if (board.getSeedsInHouse(playerTurn, i) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void nextPlayer() {
        playerTurn = (playerTurn + 1) % numberOfPlayers;
    }

    private boolean extraTurnCondition(SowState state) {
        return state.seedsToSow == 0 && state.sowingHouseOwner == playerTurn && state.sowingHouseNumber == -1;
    }

    private boolean sowingStoreCondition(SowState state) {
        return state.sowingHouseOwner == playerTurn;
    }

    private boolean captureCondition(SowState state) {
        return state.seedsToSow == 0 && state.sowingHouseOwner == playerTurn && state.seedsInHouse == 1 && state.seedsInOpposite > 0;
    }

    private void capture(int player, int houseNumber) {
        board.placeSeedsInStore(
                playerTurn,
                board.takeSeedsInHouseOpposite(player, houseNumber) + board.takeSeedsInHouse(player, houseNumber)
        );
    }

    private class SowIterator implements Iterator<SowState> {
        SowState state;

        SowIterator(SowState state) {
            this.state = state;
        }

        private void sowHouse() {
            state.seedsInHouse = board.placeSeedsInHouse(state.sowingHouseOwner, state.sowingHouseNumber, 1);
            state.seedsInOpposite = board.getSeedsInHouseOpposite(state.sowingHouseOwner, state.sowingHouseNumber);
            state.seedsToSow--;
        }

        private void sowStore() {
            state.seedsInHouse = board.placeSeedsInStore(state.sowingHouseOwner, 1);
            state.seedsInOpposite = -1;
            state.seedsToSow--;
        }

        private void nextPlayer() {
            state.sowingHouseOwner = (state.sowingHouseOwner + 1) % numberOfPlayers;
            state.sowingHouseNumber = 0;
        }

        @Override
        public SowState next() {
            SowState sowState;

            if (state.sowingHouseNumber >= board.getNumberOfHouses(state.sowingHouseOwner)) {
                if (sowingStoreCondition(state)) {
                    sowStore();
                    sowState = new SowState(state);
                    sowState.sowingHouseNumber = -1;
                    nextPlayer();
                    return sowState;
                } else {
                    nextPlayer();
                }
            }

            sowHouse();
            sowState = new SowState(state);
            state.sowingHouseNumber++;

            return sowState;
        }

        @Override
        public boolean hasNext() {
            return state.seedsToSow > 0;
        }
    }

    private class SowState {
        int sowingHouseOwner;
        int sowingHouseNumber;
        int seedsInOpposite;
        int seedsInHouse;
        int seedsToSow;

        SowState(int sowingHouseOwner, int sowingHouseNumber, int seedsInOpposite, int seedsInHouse, int seedsToSow) {
            this.sowingHouseOwner = sowingHouseOwner;
            this.sowingHouseNumber = sowingHouseNumber;
            this.seedsInOpposite = seedsInOpposite;
            this.seedsInHouse = seedsInHouse;
            this.seedsToSow = seedsToSow;
        }

        SowState(SowState other) {
            sowingHouseOwner = other.sowingHouseOwner;
            sowingHouseNumber = other.sowingHouseNumber;
            seedsInOpposite = other.seedsInOpposite;
            seedsInHouse = other.seedsInHouse;
            seedsToSow = other.seedsToSow;
        }
    }
}
