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
    
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.rack = new ArrayList<>();
    }
    
    public String getName() { return name; }
    public int getScore() { return score; }
    
    public void addScore(int points) {
        this.score += points;
    }
    
    public void setScore(int newScore) {
        this.score = newScore;
    }
    
    public void resetScore() {
        this.score = 0;
    }
    
    public List<Character> getRack() {
        return new ArrayList<>(rack);
    }
    
    protected List<Character> getInternalRack() {
        return rack;
    }
    
    public void addTile(Character tile) {
        if (tile != null) {
            rack.add(tile);
        }
    }
    
    public void addTiles(List<Character> tiles) {
        if (tiles != null) {
            rack.addAll(tiles);
        }
    }
    
    public void setTiles(List<Character> newRack) {
        if (newRack == null) {
            this.rack = new ArrayList<>();
        } else {
            this.rack = new ArrayList<>(newRack);
        }
    }
    
    public void clearRack() {
        if (this.rack != null) {
            this.rack.clear();
        }
    }
    
    public String getRackDisplay() {
        StringBuilder sb = new StringBuilder();
        for (Character c : rack) {
            sb.append(c).append(" ");
        }
        return sb.toString().trim();
    }
    
    public void fillRack(TileBag bag) {
        while (rack.size() < 7 && !bag.isEmpty()) {
            Character tile = bag.drawTile();
            if (tile != null) {
                rack.add(tile);
            }
        }
    }
    
    public void refillRack(TileBag bag) {
        fillRack(bag);
    }
    
    /**
     * Checks if player has required letters for a word.
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
    
    public void exchangeTiles(List<Character> toExchange, TileBag bag) {
        for (Character tile : toExchange) {
            if (rack.remove(tile)) {
                bag.returnTile(tile);
            }
        }
        fillRack(bag);
    }
    
    public boolean isAI() {
        return false;
    }
    
    @Override
    public String toString() {
        return name + " - Score: " + score;
    }
}
