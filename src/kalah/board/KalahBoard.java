package kalah.board;

import kalah.util.Circular2DArrayList;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class KalahBoard {
    private Circular2DArrayList<Pit> houses = new Circular2DArrayList<>();
    private ArrayList<Pit> stores = new ArrayList<>();

    public KalahBoard(int players, int startingHouses, int startingSeeds) {
        if (startingHouses == 0) {
            throw new InvalidParameterException("Need more than one house per player");
        }

        for (int i = 0; i < players; i++) {
            ArrayList<Pit> playerHouses = new ArrayList<>();

            for (int j = 0; j < startingHouses; j++) {
                playerHouses.add(new House(startingSeeds));
            }

            Store store = new Store();
            stores.add(store);

            houses.add(playerHouses);
        }
    }

    public int getNumberOfHouses(int player) {
        return houses.get(player).size();
    }


    public int getSeedsInHouse(int player, int houseNumber) {
        return houses.circularGet(houses.toCircularIndex(player, houseNumber)).getSeeds();
    }

    public int takeSeedsInHouse(int player, int houseNumber) {
        return houses.circularGet(houses.toCircularIndex(player, houseNumber)).takeSeeds();
    }

    public int takeSeedsInHouse(int player, int houseNumber, int seeds) {
        return houses.circularGet(houses.toCircularIndex(player, houseNumber)).takeSeeds(seeds);
    }

    public int placeSeedsInHouse(int player, int houseNumber, int seeds) {
        return houses.circularGet(houses.toCircularIndex(player, houseNumber)).addSeeds(seeds);
    }

    public int getSeedsInHouseOpposite(int player, int houseNumber) {
        return houses.circularGetOpposite(houses.toCircularIndex(player, houseNumber)).getSeeds();
    }

    public int takeSeedsInHouseOpposite(int player, int houseNumber) {
        return houses.circularGetOpposite(houses.toCircularIndex(player, houseNumber)).takeSeeds();
    }

    public int takeSeedsInHouseOpposite(int player, int houseNumber, int seeds) {
        return houses.circularGetOpposite(houses.toCircularIndex(player, houseNumber)).takeSeeds(seeds);
    }

    public int getSeedsInStore(int player) {
        return stores.get(player).getSeeds();
    }

    public int placeSeedsInStore(int player, int seeds) {
        return stores.get(player).addSeeds(seeds);
    }
}

