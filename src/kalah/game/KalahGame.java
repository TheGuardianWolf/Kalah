package kalah.game;

import kalah.board.House;
import kalah.board.KalahBoard;
import kalah.board.Pit;
import kalah.board.Store;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

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

        if (retVal < 0 || retVal >= board.getPlayerHouses(playerTurn).size()) {
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
            SowResult sowResult = sow(userInput);

            if (sowResult == SowResult.NEXT_TURN) {
                nextPlayer();
            }

            return TurnResult.values()[sowResult.ordinal()];
        }
        return TurnResult.GAME_OVER;
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
            for (Pit pit : board.getPlayerHouses(i)) {
                score += pit.getSeeds();
            }
            score += board.getPlayerStore(i).getSeeds();
            scores.add(score);
        }

        return scores;
    }

    public KalahBoard getBoard() {
        return board;
    }

    private boolean playerHasSeeds() {
        for (House house : board.getPlayerHouses(playerTurn)) {
            if (house.getSeeds() > 0) {
                return true;
            }
        }
        return false;
    }

    private void nextPlayer() {
        playerTurn = (playerTurn + 1) % numberOfPlayers;
    }

    private SowResult sow(int houseNumber) {
        int seedsToSow = board.getPlayerHouses(playerTurn).get(houseNumber).getSeeds();

        if (seedsToSow == 0) {
            return SowResult.EMPTY_HOUSE;
        }

        board.getPlayerHouses(playerTurn).get(houseNumber).clearSeeds();

        int sowingNumber = board.getHouseNumber(playerTurn, houseNumber + 1);
        int lastSownHouseOwner = playerTurn;

        while (seedsToSow > 0) {
            House sowingHouse = board.getHouse(sowingNumber);
            int sowingHouseOwner = board.getHouseOwner(sowingNumber);

            if (sowingStoreCondition(lastSownHouseOwner, sowingHouseOwner)) {
                board.getPlayerStore(lastSownHouseOwner).addSeed();
                if (seedsToSow == 1) {
                    return SowResult.EXTRA_TURN;
                }
            }
            else {
                if (seedsToSow == 1 && captureCondition(sowingHouseOwner, sowingHouse)) {
                    if (capture(sowingNumber)) {
                        return SowResult.NEXT_TURN;
                    }
                }
                sowingHouse.addSeed();
                sowingNumber++;
            }

            lastSownHouseOwner = sowingHouseOwner;
            seedsToSow--;
        }

        return SowResult.NEXT_TURN;
    }

    private boolean sowingStoreCondition(int lastSownHouseOwner, int sowingHouseOwner) {
        return lastSownHouseOwner != sowingHouseOwner && lastSownHouseOwner == playerTurn;
    }

    private boolean captureCondition(int sowingHouseOwner, House sowingHouse) {
        return sowingHouseOwner == playerTurn && sowingHouse.getSeeds() == 0;
    }

    private boolean capture(int sowingNumber) {
        House opponentHouse = board.getHouseOpposite(sowingNumber);
        if (opponentHouse.getSeeds() > 0) {
            Store playerStore = board.getPlayerStore(playerTurn);
            playerStore.addSeed();
            playerStore.addSeeds(opponentHouse.getSeeds());
            opponentHouse.clearSeeds();
            return true;
        }
        return false;
    }

    private enum SowResult {
        EMPTY_HOUSE,
        NEXT_TURN,
        EXTRA_TURN
    }
}
