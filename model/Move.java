package scrabble.model;

/**
 * Represents a move in Scrabble.
 *
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class Move {
    
    private int row;
    private int col;
    private boolean horizontal;
    private String word;
    private boolean pass;

    /**
     * Instantiates a new Move.
     *
     * @param row        the row
     * @param col        the col
     * @param horizontal the horizontal
     * @param word       the word
     */
    public Move(int row, int col, boolean horizontal, String word) {
        this.row = row;
        this.col = col;
        this.horizontal = horizontal;
        this.word = word;
        this.pass = false;
    }

    /**
     * Instantiates a new Move.
     */
    public Move() {
        this.pass = true;
    }

    /**
     * Gets row.
     *
     * @return the row
     */
    public int getRow() { return row; }

    /**
     * Gets col.
     *
     * @return the col
     */
    public int getCol() { return col; }

    /**
     * Is horizontal boolean.
     *
     * @return the boolean
     */
    public boolean isHorizontal() { return horizontal; }

    /**
     * Gets word.
     *
     * @return the word
     */
    public String getWord() { return word; }

    /**
     * Is pass boolean.
     *
     * @return the boolean
     */
    public boolean isPass() { return pass; }
    
    @Override
    public String toString() {
        if (pass) {
            return "PASS";
        }
        return word + " at (" + row + "," + col + ") " + (horizontal ? "H" : "V");
    }
}
