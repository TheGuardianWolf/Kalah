package kalah.components;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Board {
    private int playerHouseCount;
    private final int playerStoreCount = 1;
    private final ArrayList<PlayerPits> playerPits = new ArrayList<PlayerPits>();

    public Board(int players, int playerHouses, int startingSeeds) {
        playerHouseCount = playerHouses;
        for (int i = 0; i < players; i++) {
            playerPits.add(new PlayerPits(playerHouses, startingSeeds));
        }
    }

    // public Pit getPitGlobal(int globalHouseNumber) {
    //     return playerPits.get(globalHouseNumber / (playerHouseCount + playerStoreCount)
    // }

    public boolean sow(int player, int houseNumber) {
        int sowingPlayer = player;
        int sowingSeeds = playerPits.get(sowingPlayer)
                            .getHouse(houseNumber).getSeeds();

        if (sowingSeeds == 0) {
            throw new InvalidParameterException("House is empty");
        }

        while (sowingSeeds > 0) {
            if (houseNumber < playerPits.get(player).getHouseCount() || (player == sowingPlayer && houseNumber < playerPits.get(sowingPlayer).getHouseCount() + playerStoreCount)) {
                houseNumber++;
            }
            else {
                houseNumber = 0;
                player = (player + 1) % playerPits.size();
            }
            
            sowingSeeds--;
        }

        return false;
    }

    private void capture() {

    }
}
