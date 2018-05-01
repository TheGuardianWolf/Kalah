package kalah.board;

import kalah.util.Circular2DArrayList;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class KalahBoard {
    private int supportedPlayers;
    private Circular2DArrayList<House> houses = new Circular2DArrayList<House>();
    private ArrayList<Store> stores = new ArrayList<Store>();

    public KalahBoard(int players, int startingHouses, int startingSeeds) {
        if (startingHouses == 0) {
            throw new InvalidParameterException("Need more than one house per player");
        }

        supportedPlayers = players;

        for (int i = 0; i < players; i++) {
            ArrayList<House> playerHouses = new ArrayList<House>();

            for (int j = 0; j < startingHouses; j++) {
                playerHouses.add(new House(startingSeeds));
            }

            Store store = new Store();
            stores.add(store);

            houses.add(playerHouses);
        }
    }

    public int getHouseOwner(int circularIndex) {
        return houses.To2DIndex(circularIndex);
    }

    public int getHouseNumber(int player, int index) {
        return houses.ToCircularIndex(player, index);
    }

    public House getHouse(int houseNumber) {
        return houses.circularGet(houseNumber);
    }

    public House getHouseOpposite(int houseNumber) {
        return houses.circularGetOpposite(houseNumber);
    }

    public House getHouseNextOpponent(int player, int houseNumber) {
        return houses.get((player + 1) % supportedPlayers).get(houseNumber);
    }

    public Store getPlayerStore(int player) {
        return stores.get(player);
    }

    public List<House> getPlayerHouses(int player) {
        return houses.get(player);
    }
}
