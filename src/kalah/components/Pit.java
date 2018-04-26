package kalah.components;

public class Pit {
    protected int seeds;
    protected int owner;

    Pit(int seeds/*, int owner*/) {
        this.seeds = seeds;
        // this.owner = owner;
    }

    public int getOwner() {
        return owner;
    }

    public int getSeeds() {
        return seeds;
    }

    public int addSeed() {
        this.seeds++;
        return this.seeds;
    }

    public int addSeeds(int seeds) {
        this.seeds += seeds;
        return this.seeds;
    }

    public int removeSeed() {
        this.seeds--;
        return this.seeds;
    }

    public void clearSeeds() {
        seeds = 0;
    }

    @Override
    public String toString() {
        return String.format("%2d", seeds);
    }
}
