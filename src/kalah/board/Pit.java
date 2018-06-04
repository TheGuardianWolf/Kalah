package kalah.board;

import java.security.InvalidParameterException;

public abstract class Pit {
    private int seeds;

    protected Pit(int seeds) {
        this.seeds = seeds;
    }

    public int getSeeds() {
        return seeds;
    }

    public int addSeed() {
        return addSeeds(1);
    }

    public int addSeeds(int seeds) {
        this.seeds += seeds;
        return this.seeds;
    }

    public int takeSeeds() {
        int originalSeeds = seeds;
        seeds = 0;
        return originalSeeds;
    }

    public int takeSeeds(int seeds) {
        if (this.seeds - seeds >= 0) {
            this.seeds -= seeds;
        }
        else {
            throw new InvalidParameterException();
        }

        return seeds;
    }

    @Override
    public String toString() {
        return String.format("%d", seeds);
    }
}
