package kalah.game;

class SowState {
    int sowingHouseOwner;
    int sowingHouseNumber;
    int seedsInOpposite;
    int seedsInHouse;
    int seedsToSow;

    SowState(int sowingHouseOwner, int sowingHouseNumber, int seedsInOpposite, int seedsInHouse, int seedsToSow) {
        this.sowingHouseOwner = sowingHouseOwner;
        this.sowingHouseNumber = sowingHouseNumber;
        this.seedsInOpposite = seedsInOpposite;
        this.seedsInHouse = seedsInHouse;
        this.seedsToSow = seedsToSow;
    }

    SowState(SowState other) {
        sowingHouseOwner = other.sowingHouseOwner;
        sowingHouseNumber = other.sowingHouseNumber;
        seedsInOpposite = other.seedsInOpposite;
        seedsInHouse = other.seedsInHouse;
        seedsToSow = other.seedsToSow;
    }
}