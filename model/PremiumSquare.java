package scrabble.model;

import java.io.Serializable;

/**
 * Enum representing premium squares on the Scrabble board.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public enum PremiumSquare implements Serializable {
    
    NORMAL(1, 1),
    DOUBLE_LETTER(2, 1),
    TRIPLE_LETTER(3, 1),
    DOUBLE_WORD(1, 2),
    TRIPLE_WORD(1, 3);
    
    private final int letterMultiplier;
    private final int wordMultiplier;
    
    PremiumSquare(int letterMultiplier, int wordMultiplier) {
        this.letterMultiplier = letterMultiplier;
        this.wordMultiplier = wordMultiplier;
    }
    
    public int getLetterMultiplier() {
        return letterMultiplier;
    }
    
    public int getWordMultiplier() {
        return wordMultiplier;
    }
}
