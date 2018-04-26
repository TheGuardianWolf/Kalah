package kalah.components;

import java.util.ArrayList;

public class PlayerPits {
    private final ArrayList<Pit> pits = new ArrayList<Pit>();
    private final ArrayList<House> houses = new ArrayList<House>();
    private Store store;

    public PlayerPits(int numberOfHouses, int startingSeeds) {
        for (int i = 0; i < numberOfHouses; i++) {
            pits.add(new House(startingSeeds));
        }
        store = new Store();

        pits.addAll(pits);
        pits.add(store);
    }

    public int seedSum() {
        int sum = 0;
        for (int i = 0; i < pits.size(); i++) {
            sum += pits.get(i).getSeeds();
        }
        return sum;
    }

    public boolean areHousesEmpty() {
        for (int i = 0; i < houses.size(); i++) {
            if (houses.get(i).getSeeds() != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean ownsPit(Pit pit) {
        return pits.contains(pit);
    }

    public int getPitIndex(Pit pit) {
        return pits.indexOf(pit);
    }

    public Pit getPit(int index) {
        return pits.get(index);
    }

    public int getPitCount() {
        return pits.size();
    }

    public House getHouse(int index) {
        return houses.get(index);
    }

    public int getHouseCount() {
        return houses.size();
    }

    public Store getStore() {
        return store;
    }
}