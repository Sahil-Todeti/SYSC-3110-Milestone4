package scrabble.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the pool of tiles in Scrabble.
 *
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class TileBag implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<Character> bag;

    /**
     * Instantiates a new Tile bag.
     */
    public TileBag() {
        bag = new ArrayList<>();
        initializeTiles();
        Collections.shuffle(bag);
    }
    
    private void initializeTiles() {
        addTiles('A', 9);
        addTiles('B', 2);
        addTiles('C', 2);
        addTiles('D', 4);
        addTiles('E', 12);
        addTiles('F', 2);
        addTiles('G', 3);
        addTiles('H', 2);
        addTiles('I', 9);
        addTiles('J', 1);
        addTiles('K', 1);
        addTiles('L', 4);
        addTiles('M', 2);
        addTiles('N', 6);
        addTiles('O', 8);
        addTiles('P', 2);
        addTiles('Q', 1);
        addTiles('R', 6);
        addTiles('S', 4);
        addTiles('T', 6);
        addTiles('U', 4);
        addTiles('V', 2);
        addTiles('W', 2);
        addTiles('X', 1);
        addTiles('Y', 2);
        addTiles('Z', 1);
    }
    
    private void addTiles(char letter, int count) {
        for (int i = 0; i < count; i++) {
            bag.add(letter);
        }
    }

    /**
     * Draw tile character.
     *
     * @return the character
     */
    public Character drawTile() {
        if (bag.isEmpty()) {
            return null;
        }
        return bag.remove(bag.size() - 1);
    }

    /**
     * Draw tiles list.
     *
     * @param total the total
     * @return the list
     */
    public List<Character> drawTiles(int total) {
        List<Character> drawn = new ArrayList<>();
        for (int i = 0; i < total && !bag.isEmpty(); i++) {
            drawn.add(bag.remove(bag.size() - 1));
        }
        return drawn;
    }

    /**
     * Return tile.
     *
     * @param tile the tile
     */
    public void returnTile(Character tile) {
        if (tile != null) {
            bag.add(tile);
            Collections.shuffle(bag);
        }
    }

    /**
     * Return tiles.
     *
     * @param tiles the tiles
     */
    public void returnTiles(List<Character> tiles) {
        if (tiles != null && !tiles.isEmpty()) {
            bag.addAll(tiles);
            Collections.shuffle(bag);
        }
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public boolean isEmpty() {
        return bag.isEmpty();
    }

    /**
     * Size int.
     *
     * @return the int
     */
    public int size() {
        return bag.size();
    }

    /**
     * Tiles remaining int.
     *
     * @return the int
     */
    public int tilesRemaining() {
        return bag.size();
    }

    /**
     * Reset.
     */
    public void reset() {
        bag.clear();
        initializeTiles();
        Collections.shuffle(bag);
    }
    
    @Override
    public String toString() {
        return "TileBag: " + bag.size() + " tiles remaining";
    }
}
