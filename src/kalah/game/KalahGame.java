package kalah.game;

import kalah.board.KalahBoard;

import java.util.*;

public abstract class KalahGame extends Game<SowState> {
    private int startingHouses;
    private int startingSeeds;
    private KalahBoard board;

    public KalahGame(int numberOfPlayers, int startingHouses, int startingSeeds) {
        super(numberOfPlayers);
        this.startingHouses = startingHouses;
        this.startingSeeds = startingSeeds;
    }

    protected abstract boolean sowingStoreCondition(SowState state);

    protected boolean playerHasSeeds() {
        for (int i = 0; i < board.getNumberOfHouses(getPlayerTurn()); i++) {
            if (board.getSeedsInHouse(getPlayerTurn(), i) > 0) {
                return true;
            }
        }
        return false;
    }

    protected void capture(int player, int houseNumber) {
        board.placeSeedsInStore(
                getPlayerTurn(),
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
            state.sowingHouseOwner = (state.sowingHouseOwner + 1) % getNumberOfPlayers();
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

    @Override
    protected void beforeTurnIteratorNext(int userInput) {
        board.takeSeedsInHouse(getPlayerTurn(), userInput, 1);
    }

    @Override
    protected boolean validateInput(int input) {
        return input < 0 || input >= board.getNumberOfHouses(getPlayerTurn());
    }

    @Override
    protected Iterator<SowState> createIterator(SowState state) {
        return new SowIterator(new SowState(state));
    }

    @Override
    protected SowState initialiseState(int turnInput) {
        return new SowState(
                getPlayerTurn(),
                turnInput + 1,
                board.getSeedsInHouseOpposite(getPlayerTurn(), turnInput + 1),
                board.getSeedsInHouse(getPlayerTurn(), turnInput + 1),
                board.getSeedsInHouse(getPlayerTurn(), turnInput)
        );
    }

    @Override
    protected boolean isValidMove(int userInput) {
        return board.getSeedsInHouse(getPlayerTurn(), userInput) > 0;
    }

    @Override
    public int parseInput(String input) {
        int retVal =  Integer.parseInt(input) - 1;

        if (retVal < 0 || retVal >= board.getNumberOfHouses(getPlayerTurn())) {
            throw new InputMismatchException("This is not a valid player house");
        }

        return retVal;
    }

    @Override
    public void newGame() {
        board = new KalahBoard(getNumberOfPlayers(), startingHouses, startingSeeds);
    }

    @Override
    protected int getScore(int player) {
        int score = 0;
        for (int i = 0; i < board.getNumberOfHouses(player); i++) {
            score += board.getSeedsInHouse(player, i);
        }
        score += board.getSeedsInStore(player);

        return score;
    }

    public KalahBoard getBoard() {
        return board;
    }

    @Override
    public boolean isGameOver() {
        return !playerHasSeeds();
    }
}
