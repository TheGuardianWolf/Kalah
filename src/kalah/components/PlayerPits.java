package kalah.components;

import java.util.ArrayList;

public class PlayerPits {
    private final ArrayList<Pit> houses = new ArrayList<Pit>();
	private final Pit store = new Store();

    public PlayerPits(int numberOfHouses, int startingSeeds) {
        for (int i = 0; i < numberOfHouses; i++) {
            houses.add(new House(startingSeeds));
        }
    }

    public Pit getHouse(int index) {
        return houses.get(index);
    }

    public int getHouseCount() {
        return houses.size();
    }

    public Pit getStore() {
        return store;
    }
}
