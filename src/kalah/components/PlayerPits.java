package kalah.components;

import java.util.ArrayList;

public class PlayerPits {
    private final ArrayList<Pit> pits = new ArrayList<Pit>();
    private final ArrayList<House> houses = new ArrayList<House>();
    private Store store;

    public PlayerPits(int numberOfHouses, int startingSeeds) {
        for (int i = 0; i < numberOfHouses; i++) {
            houses.add(new House(startingSeeds));
        }
        store = new Store();

        pits.addAll(houses);
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
        for (House house : houses) {
            if (house.getSeeds() != 0) {
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("| ");

        for (int i = 0; i < houses.size(); i++) {
            s.append(String.format("%d[", i + 1));
            s.append(houses.get(i).toString());
            s.append("]");
            if (i != houses.size() - 1) {
                s.append(" | ");
            }
            else {
                s.append(" |");
            }
        }

        return s.toString();
    }

    public String toStringReversed() {
        StringBuilder s = new StringBuilder("| ");
        
        for (int i = houses.size() - 1; i >= 0; i--) {
            s.append(String.format("%d[", i + 1));
            s.append(houses.get(i).toString());
            s.append("]");

            if (i != 0) {
                s.append(" | ");
            }
            else {
                s.append(" |");
            }
        }

        return s.toString();
    }
}
