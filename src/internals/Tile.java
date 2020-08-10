package internals;

import internals.meeple.*;
import internals.TribesEnum.PlayerColor;

/**
 * Class
 */
public class Tile {
    public final int value;
    private PlayerColor owner = null;
    // private Meeple assassin;
    public MeepleGroup builder;
    public MeepleGroup vizier;
    public MeepleGroup elder;

    /**
     * Constructor, nothing special.
     */
    public Tile(int value){
        this.value = value;
        builder = new Builder();
        vizier = new Vizier();
        elder = new Elder();
    }

    /**
     * Check if current tile is empty.
     * @return boolean
     */
    public boolean isEmpty(){
        int totalPopulation = builder.getPopulation() + vizier.getPopulation() + elder.getPopulation();
        return totalPopulation == 0;
    }


    /**
     * Get the current owner of this tile
     * @return PlayerColor enum
     */
    public PlayerColor getOwner() {
        return owner;
    }

    /**
     * Set the current owner of the tile
     * @param owner Owner of the tile
     */
    public void setOwner(PlayerColor owner) {
        this.owner = owner;
    }


    /**
     * Clean the tile and clear the meeples in it
     */
    public void cleanTile(){
        builder.setPopulation(0);
        vizier.setPopulation(0);
        elder.setPopulation(0);
    }
}
