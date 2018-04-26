package kalah.components;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Game {
    private int playerTurn = 0;
    private int numberOfPlayers;
    private int playerHouses;
    private int startingSeeds;
    private Board board;

    public Game(int players, int playerHouses, int startingSeeds) {
        this.numberOfPlayers = players;
        this.playerHouses = playerHouses;
        this.startingSeeds = startingSeeds;

        newGame();
    }

    public void newGame() {
        board = new Board(numberOfPlayers, playerHouses, startingSeeds);
    }

    public void takeTurn(String userInput) {
        int houseNumber = Integer.parseInt(userInput) - 1;
        Pit lastSown = board.sow(playerTurn, houseNumber);

        if (!Store.class.isInstance(lastSown)) {
            if (lastSown.getSeeds() == 1 && board.getPlayerPit(playerTurn).ownsPit(lastSown) && House.class.isInstance(lastSown)) {
                board.capture((House) lastSown);
            }
            playerTurn = (playerTurn + 1) % numberOfPlayers;
        }
    }

    public boolean hasTurn(int player) {
        return !board.arePlayerHousesEmpty(playerTurn);
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public String boardString() {
        return board.toString();
    }

    public String scoreString() {
        return board.scoreString();
    }

    public int getHighestScorePlayer() {
        int iMax = 0;
        int max = 0;

        for (int i = 0; i < numberOfPlayers; i++) {
            int playerScore = board.getPlayerPit(i).seedSum();
            if (playerScore > max) {
                max = playerScore;
                iMax = i;
            }
            else if (playerScore == max) {
                return -1;
            }
        }

        return iMax;
    }
}
