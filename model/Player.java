package scrabble.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Scrabble game.
 * Manages player rack, score, and tile operations.
 *
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class Player implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int score;
    private List<Character> rack;

    /**
     * Instantiates a new Player.
     *
     * @param name the name
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.rack = new ArrayList<>();
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() { return score; }

    /**
     * Add score.
     *
     * @param points the points
     */
    public void addScore(int points) {
        this.score += points;
    }

    /**
     * Sets score.
     *
     * @param newScore the new score
     */
    public void setScore(int newScore) {
        this.score = newScore;
    }

    /**
     * Reset score.
     */
    public void resetScore() {
        this.score = 0;
    }

    /**
     * Gets rack.
     *
     * @return the rack
     */
    public List<Character> getRack() {
        return new ArrayList<>(rack);
    }

    /**
     * Gets internal rack.
     *
     * @return the internal rack
     */
    protected List<Character> getInternalRack() {
        return rack;
    }

    /**
     * Add tile.
     *
     * @param tile the tile
     */
    public void addTile(Character tile) {
        if (tile != null) {
            rack.add(tile);
        }
    }

    /**
     * Add tiles.
     *
     * @param tiles the tiles
     */
    public void addTiles(List<Character> tiles) {
        if (tiles != null) {
            rack.addAll(tiles);
        }
    }

    /**
     * Sets tiles.
     *
     * @param newRack the new rack
     */
    public void setTiles(List<Character> newRack) {
        if (newRack == null) {
            this.rack = new ArrayList<>();
        } else {
            this.rack = new ArrayList<>(newRack);
        }
    }

    /**
     * Clear rack.
     */
    public void clearRack() {
        if (this.rack != null) {
            this.rack.clear();
        }
    }

    /**
     * Gets rack display.
     *
     * @return the rack display
     */
    public String getRackDisplay() {
        StringBuilder sb = new StringBuilder();
        for (Character c : rack) {
            sb.append(c).append(" ");
        }
        return sb.toString().trim();
    }

    /**
     * Fill rack.
     *
     * @param bag the bag
     */
    public void fillRack(TileBag bag) {
        while (rack.size() < 7 && !bag.isEmpty()) {
            Character tile = bag.drawTile();
            if (tile != null) {
                rack.add(tile);
            }
        }
    }

    /**
     * Refill rack.
     *
     * @param bag the bag
     */
    public void refillRack(TileBag bag) {
        fillRack(bag);
    }

    /**
     * Checks if player has required letters for a word.
     *
     * @param word       the word
     * @param board      the board
     * @param row        the row
     * @param col        the col
     * @param horizontal the horizontal
     * @return the boolean
     */
    public boolean hasLetters(String word, Board board, int row, int col, boolean horizontal) {
        List<Character> needed = new ArrayList<>();
        
        for (int i = 0; i < word.length(); i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;
            
            if (r < 0 || r >= 15 || c < 0 || c >= 15) {
                return false;
            }
            
            Character boardTile = board.get(r, c);
            char wordLetter = Character.toUpperCase(word.charAt(i));
            
            if (boardTile == null || boardTile == '_') {
                needed.add(wordLetter);
            }
        }
        
        List<Character> rackCopy = new ArrayList<>(rack);
        for (Character letter : needed) {
            if (rackCopy.contains(letter)) {
                rackCopy.remove(letter);
            } else {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Removes letters from rack after placement.
     *
     * @param word       the word
     * @param board      the board
     * @param row        the row
     * @param col        the col
     * @param horizontal the horizontal
     * @return the boolean
     */
    public boolean removeLetters(String word, Board board, int row, int col, boolean horizontal) {
        List<Character> toRemove = new ArrayList<>();
        
        for (int i = 0; i < word.length(); i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;
            
            if (r < 0 || r >= 15 || c < 0 || c >= 15) {
                return false;
            }
            
            Character boardTile = board.get(r, c);
            char wordLetter = Character.toUpperCase(word.charAt(i));
            
            if (boardTile == null || boardTile == '_') {
                toRemove.add(wordLetter);
            }
        }
        
        for (Character letter : toRemove) {
            if (!rack.remove(letter)) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Exchange tiles.
     *
     * @param toExchange the to exchange
     * @param bag        the bag
     */
    public void exchangeTiles(List<Character> toExchange, TileBag bag) {
        for (Character tile : toExchange) {
            if (rack.remove(tile)) {
                bag.returnTile(tile);
            }
        }
        fillRack(bag);
    }

    /**
     * Is ai boolean.
     *
     * @return the boolean
     */
    public boolean isAI() {
        return false;
    }
    
    @Override
    public String toString() {
        return name + " - Score: " + score;
    }

    /**
     * Gets rack size.
     *
     * @return the rack size
     */
    public int getRackSize() {
        return this.rack.size();
    }
}
