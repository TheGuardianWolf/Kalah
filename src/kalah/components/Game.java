package kalah.components;

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
    }

    public void newGame() {
        board = new Board(numberOfPlayers, playerHouses, startingSeeds);
    }

    public void takeTurn(String userInput) {
        // if (board.arePlayerHousesEmpty(playerTurn)) {
        //     throw new
        // }

        int houseNumber = Integer.parseInt(userInput);
        Pit lastSown = board.sow(playerTurn, houseNumber);

        boolean nextPlayer = true;

        if (!Store.class.isInstance(lastSown)) {
            if (lastSown.getSeeds() == 1 && House.class.isInstance(lastSown)) {
                nextPlayer = nextPlayer && board.captureOpposite((House) lastSown);
            }
        }
        else {
            nextPlayer = false;
        }

        if (nextPlayer) {
            playerTurn = (playerTurn + 1) % numberOfPlayers;
        }
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public String boardString() {
        return board.toString();
    }
}
