package internals.meeple;

/**
 * class for Elder (white) and its action
 */
public class Elder extends MeepleGroup{
    /**
     * Default Constructor for Elder
     */
    public Elder() {
        super(0);
    }
    /**
     * Constructor for Elder
     * @param population population of the new Elder
     */
    public Elder(int population) {
        super(population);
    }
}
