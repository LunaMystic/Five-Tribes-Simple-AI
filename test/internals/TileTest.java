package internals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    @Test
    void testTileConstruction() {
        Tile testTile = new Tile(15);
        assertEquals(15, testTile.value);
    }
}