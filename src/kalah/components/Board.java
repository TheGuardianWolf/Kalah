package kalah.components;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Board {
    private final ArrayList<PlayerPits> playerPits = new ArrayList<PlayerPits>();

    public Board(int players, int playerHouses, int startingSeeds) {
        if (playerHouses == 0) {
            throw new InvalidParameterException("Need more than one house per player");
        }

        for (int i = 0; i < players; i++) {
            playerPits.add(new PlayerPits(playerHouses, startingSeeds));
        }
    }

    public PlayerPits getPlayerPit(int index) {
        return playerPits.get(index);
    }

    public Pit sow(int player, int houseNumber) {
        int sowingPlayer = player;
        int sowingSeeds = playerPits.get(player).getHouse(houseNumber).getSeeds();
        int pitNumber = houseNumber;

        if (sowingSeeds == 0) {
            throw new InvalidParameterException("House is empty");
        }

        playerPits.get(player).getHouse(houseNumber).clearSeeds();

        Pit pit = null;
        while (sowingSeeds > 0) {
            if (pitNumber < playerPits.get(sowingPlayer).getPitCount()) {
                pitNumber++;
                pit = playerPits.get(sowingPlayer).getPit(pitNumber);
            } else {
                pitNumber = 0;
                sowingPlayer = (sowingPlayer + 1) % playerPits.size();
            }

            if (pit != null) {
                if (House.class.isInstance(pit) || (sowingPlayer == player && Store.class.isInstance(pit))) {
                    sowingSeeds--;
                    pit.addSeed();
                }
            }
        }

        return pit;
    }

    public boolean captureOpposite(House house) {
        int player = -1;
        for (int i = 0; i < playerPits.size(); i++) {
            if (playerPits.get(i).ownsPit(house)) {
                player = i;
                break;
            }
        }

        int oppositePlayer = (player + 1) % playerPits.size();
        int houseIndex = playerPits.get(player).getPitIndex(house);

        House opposite = playerPits.get(oppositePlayer)
                .getHouse(playerPits.get(oppositePlayer).getHouseCount() - 1 - houseIndex);

        if (opposite.getSeeds() > 0) {
            house.addSeeds(opposite.getSeeds());
            opposite.clearSeeds();
            return true;
        }
        
        return false;
    }

    public boolean arePlayerHousesEmpty(int player) {
        return playerPits.get(player).areHousesEmpty();
    }
}