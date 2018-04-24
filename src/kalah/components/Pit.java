package kalah.components;

public class Pit {
    private int seeds;
    
    Pit(int seeds) {
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
        this.seeds--;
        return this.seeds;
    }

    public void clearSeeds() {
        seeds = 0;
    }
}
