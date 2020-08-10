package internals.meeple;

/**
 * Meeple Class for general Meeple
 */
public class MeepleGroup {
    private int population;
    public MeepleGroup(){
        this.population = 0;
    }

    public MeepleGroup(int population) {
        this.population = population;
    }

    public int getPopulation() {
        return population;
    }

    public int setPopulation(int population){
        this.population = population;
        return this.population;
    }

    public int addMember(){
        this.population = this.population + 1;
        return this.population;
    }

    public int killMember(){
        this.population = this.population - 1;
        return this.population;
    }
}
