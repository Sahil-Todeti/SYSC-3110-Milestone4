package scrabble.model;

import java.io.Serializable;

/**
 * Represents a single Scrabble tile with a letter and point value.
 *
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class Tile implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final char letter;
    private final int value;
    private final boolean blank;

    /**
     * Instantiates a new Tile.
     *
     * @param letter the letter
     */
    public Tile(char letter) {
        this.letter = Character.toUpperCase(letter);
        this.blank = false;
        this.value = calculateValue(this.letter);
    }

    private Tile(char letter, int value, boolean blank) {
        this.letter = letter;
        this.value = value;
        this.blank = blank;
    }

    /**
     * Create blank tile.
     *
     * @return the tile
     */
    public static Tile createBlank() {
        return new Tile('_', 0, true);
    }

    private int calculateValue(char letter) {
        switch (letter) {
            case 'A': case 'E': case 'I': case 'O': case 'U': 
            case 'L': case 'N': case 'S': case 'T': case 'R':
                return 1;
            case 'D': case 'G':
                return 2;
            case 'B': case 'C': case 'M': case 'P':
                return 3;
            case 'F': case 'H': case 'V': case 'W': case 'Y':
                return 4;
            case 'K':
                return 5;
            case 'J': case 'X':
                return 8;
            case 'Q': case 'Z':
                return 10;
            default:
                return 0;
        }
    }

    /**
     * Gets letter.
     *
     * @return the letter
     */
    public char getLetter() { return letter; }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() { return value; }

    /**
     * Is blank boolean.
     *
     * @return the boolean
     */
    public boolean isBlank() { return blank; }

    @Override
    public String toString() {
        return blank ? "_" : String.valueOf(letter);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tile)) return false;
        Tile other = (Tile) obj;
        return this.letter == other.letter && this.blank == other.blank;
    }

    @Override
    public int hashCode() {
        return (letter + "" + blank).hashCode();
    }

    /**
     * To simple string string.
     *
     * @return the string
     */
    public String toSimpleString() {
        return String.valueOf(letter);
    }
}
