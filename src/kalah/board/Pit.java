package kalah.board;

public class Pit {
    private int seeds;

    Pit(int seeds/*, int owner*/) {
        this.seeds = seeds;
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
        if (this.seeds > 0) {
            this.seeds--;
        }
        return this.seeds;
    }

    public void clearSeeds() {
        seeds = 0;
    }

    @Override
    public String toString() {
        return String.format("%d", seeds);
    }
}
