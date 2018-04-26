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
            pitNumber++;
            if (pitNumber < playerPits.get(sowingPlayer).getPitCount()) {
                pit = playerPits.get(sowingPlayer).getPit(pitNumber);
            } else {
                pitNumber = 0;
                sowingPlayer = (sowingPlayer + 1) % playerPits.size();
                pit = playerPits.get(sowingPlayer).getPit(pitNumber);
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

    public boolean capture(House house) {
        int player = -1;
        for (int i = 0; i < playerPits.size(); i++) {
            if (playerPits.get(i).ownsPit(house)) {
                player = i;
                break;
            }
        }

        int oppositePlayer = (player + 1) % playerPits.size();
        int houseIndex = playerPits.get(player).getPitIndex(house);
        Store store = playerPits.get(player).getStore();

        House opposite = playerPits.get(oppositePlayer)
                .getHouse(playerPits.get(oppositePlayer).getHouseCount() - 1 - houseIndex);

        if (opposite.getSeeds() == 0) {
            return false;
        }

        store.addSeeds(opposite.getSeeds());
        opposite.clearSeeds();
        store.addSeeds(house.getSeeds());
        house.clearSeeds();

        return true;
    }

    public boolean arePlayerHousesEmpty(int player) {
        return playerPits.get(player).areHousesEmpty();
    }

    public String scoreString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < playerPits.size(); i++) {
            s.append(String.format("\tplayer %d:%d", i + 1, playerPits.get(i).seedSum()));
            if (i != playerPits.size() - 1) {
                s.append(System.lineSeparator());
            }
        }
        return s.toString();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("+----+-------+-------+-------+-------+-------+-------+----+");
        s.append(System.lineSeparator());

        s.append("| P2 ");
        s.append(playerPits.get(1).toStringReversed());
        s.append(String.format(" %s |", playerPits.get(0).getStore().toString()));
        s.append(System.lineSeparator());

        s.append("|    |-------+-------+-------+-------+-------+-------|    |");
        s.append(System.lineSeparator());

        s.append(String.format("| %s ", playerPits.get(1).getStore().toString()));
        s.append(playerPits.get(0).toString());
        s.append(" P1 |");
        s.append(System.lineSeparator());

        s.append("+----+-------+-------+-------+-------+-------+-------+----+");

        return s.toString();
    }
}
