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
    
    public Move(int row, int col, boolean horizontal, String word) {
        this.row = row;
        this.col = col;
        this.horizontal = horizontal;
        this.word = word;
        this.pass = false;
    }
    
    public Move() {
        this.pass = true;
    }
    
    public int getRow() { return row; }
    public int getCol() { return col; }
    public boolean isHorizontal() { return horizontal; }
    public String getWord() { return word; }
    public boolean isPass() { return pass; }
    
    @Override
    public String toString() {
        if (pass) {
            return "PASS";
        }
        return word + " at (" + row + "," + col + ") " + (horizontal ? "H" : "V");
    }
}
