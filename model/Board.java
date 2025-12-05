package scrabble.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Scrabble game board (15x15).
 * Supports custom board configurations and serialization.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class Board implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static final int SIZE = 15;
    
    private char[][] grid;
    private PremiumSquare[][] premiumGrid;
    private boolean[][] usedPremium;
    private String boardName;
    
    /**
     * Default constructor - initializes standard board.
     */
    public Board() {
        this(null);
    }
    
    /**
     * Constructor with custom board configuration.
     * 
     * @param config BoardConfig with custom premium square layout (null for standard)
     */
    public Board(BoardConfig config) {
        initializeGrids();
        
        if (config == null) {
            this.boardName = "Standard Board";
            initializeStandardPremiumSquares();
        } else {
            this.boardName = config.getName();
            applyBoardConfig(config);
        }
    }
    
    private void initializeGrids() {
        grid = new char[SIZE][SIZE];
        premiumGrid = new PremiumSquare[SIZE][SIZE];
        usedPremium = new boolean[SIZE][SIZE];
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = '_';
                premiumGrid[i][j] = PremiumSquare.NORMAL;
                usedPremium[i][j] = false;
            }
        }
    }
    
    private void applyBoardConfig(BoardConfig config) {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                premiumGrid[r][c] = PremiumSquare.NORMAL;
            }
        }
        
        applyPremiumList(config.getTripleWordPositions(), PremiumSquare.TRIPLE_WORD);
        applyPremiumList(config.getDoubleWordPositions(), PremiumSquare.DOUBLE_WORD);
        applyPremiumList(config.getTripleLetterPositions(), PremiumSquare.TRIPLE_LETTER);
        applyPremiumList(config.getDoubleLetterPositions(), PremiumSquare.DOUBLE_LETTER);
    }
    
    private void applyPremiumList(List<int[]> positions, PremiumSquare type) {
        if (positions == null) return;
        
        for (int[] pos : positions) {
            if (pos != null && pos.length == 2) {
                int r = pos[0];
                int c = pos[1];
                if (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
                    premiumGrid[r][c] = type;
                }
            }
        }
    }
    
    private void initializeStandardPremiumSquares() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                premiumGrid[i][j] = PremiumSquare.NORMAL;
            }
        }
        
        // Triple Word Score positions
        int[][] twsPositions = {
            {0,0}, {0,7}, {0,14}, {7,0}, {7,14}, {14,0}, {14,7}, {14,14}
        };
        for (int[] pos : twsPositions) {
            premiumGrid[pos[0]][pos[1]] = PremiumSquare.TRIPLE_WORD;
        }
        
        // Double Word Score positions
        int[][] dwsPositions = {
            {1,1}, {1,13}, {2,2}, {2,12}, {3,3}, {3,11}, {4,4}, {4,10},
            {10,4}, {10,10}, {11,3}, {11,11}, {12,2}, {12,12}, {13,1}, {13,13}
        };
        for (int[] pos : dwsPositions) {
            premiumGrid[pos[0]][pos[1]] = PremiumSquare.DOUBLE_WORD;
        }
        
        // Triple Letter Score positions
        int[][] tlsPositions = {
            {1,5}, {1,9}, {5,1}, {5,5}, {5,9}, {5,13},
            {9,1}, {9,5}, {9,9}, {9,13}, {13,5}, {13,9}
        };
        for (int[] pos : tlsPositions) {
            premiumGrid[pos[0]][pos[1]] = PremiumSquare.TRIPLE_LETTER;
        }
        
        // Double Letter Score positions
        int[][] dlsPositions = {
            {0,3}, {0,11}, {2,6}, {2,8}, {3,0}, {3,7}, {3,14},
            {6,2}, {6,6}, {6,8}, {6,12}, {7,3}, {7,11},
            {8,2}, {8,6}, {8,8}, {8,12}, {11,0}, {11,7}, {11,14},
            {12,6}, {12,8}, {14,3}, {14,11}
        };
        for (int[] pos : dlsPositions) {
            premiumGrid[pos[0]][pos[1]] = PremiumSquare.DOUBLE_LETTER;
        }
    }
    
    /**
     * Gets the character at a position on the board.
     */
    public Character get(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return null;
        }
        char c = grid[row][col];
        return c == '_' ? null : c;
    }
    
    /**
     * Gets the premium square type at a position.
     */
    public PremiumSquare getPremium(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return PremiumSquare.NORMAL;
        }
        return premiumGrid[row][col];
    }
    
    /**
     * Checks if premium square has been used.
     */
    public boolean isPremiumUsed(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return true;
        }
        return usedPremium[row][col];
    }
    
    public String getBoardName() { return boardName; }
    public int getSize() { return SIZE; }
    public char[][] getGrid() { return grid; }
    public PremiumSquare[][] getPremiumGrid() { return premiumGrid; }
    public boolean[][] getUsedPremium() { return usedPremium; }
    
    public void setGrid(char[][] newGrid) {
        if (newGrid != null && newGrid.length == SIZE) {
            boolean validGrid = true;
            for (char[] row : newGrid) {
                if (row == null || row.length != SIZE) {
                    validGrid = false;
                    break;
                }
            }
            if (validGrid) {
                this.grid = newGrid;
            }
        }
    }
    
    public void setPremiumGrid(PremiumSquare[][] newPremiumGrid) {
        if (newPremiumGrid != null && newPremiumGrid.length == SIZE) {
            boolean validGrid = true;
            for (PremiumSquare[] row : newPremiumGrid) {
                if (row == null || row.length != SIZE) {
                    validGrid = false;
                    break;
                }
            }
            if (validGrid) {
                this.premiumGrid = newPremiumGrid;
            }
        }
    }
    
    public void setUsedPremium(boolean[][] newUsedPremium) {
        if (newUsedPremium != null && newUsedPremium.length == SIZE) {
            boolean validArray = true;
            for (boolean[] row : newUsedPremium) {
                if (row == null || row.length != SIZE) {
                    validArray = false;
                    break;
                }
            }
            if (validArray) {
                this.usedPremium = newUsedPremium;
            }
        }
    }
    
    public void markPremiumUsed(int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            usedPremium[row][col] = true;
        }
    }
    
    public void placeTile(int row, int col, char letter) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            grid[row][col] = Character.toUpperCase(letter);
        }
    }
    
    public boolean isEmpty() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] != '_') {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean fits(int row, int col, boolean horizontal, int length) {
        if (horizontal) {
            return col + length <= SIZE;
        } else {
            return row + length <= SIZE;
        }
    }
    
    /**
     * Validates word placement.
     */
    public boolean isValidPlacement(int row, int col, boolean horizontal, String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        
        // Check bounds
        if (horizontal) {
            if (col + word.length() > SIZE) return false;
        } else {
            if (row + word.length() > SIZE) return false;
        }
        
        // Check for overlap conflicts
        for (int i = 0; i < word.length(); i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;
            
            char boardChar = grid[r][c];
            char wordChar = Character.toUpperCase(word.charAt(i));
            
            if (boardChar != '_' && boardChar != wordChar) {
                return false;
            }
        }
        
        // First move must use center (7,7)
        if (isEmpty()) {
            if (horizontal) {
                return col <= 7 && col + word.length() > 7;
            } else {
                return row <= 7 && row + word.length() > 7;
            }
        }
        
        // Subsequent moves must connect to existing tiles
        for (int i = 0; i < word.length(); i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;
            
            if (grid[r][c] != '_') {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Places a word on the board.
     */
    public List<Character> placeWord(int row, int col, boolean horizontal, String word, Player player) {
        List<Character> used = new ArrayList<>();
        
        player.removeLetters(word, this, row, col, horizontal);
        
        for (int i = 0; i < word.length(); i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;
            char letter = Character.toUpperCase(word.charAt(i));
            
            grid[r][c] = letter;
            
            if (premiumGrid[r][c] != PremiumSquare.NORMAL && !usedPremium[r][c]) {
                markPremiumUsed(r, c);
            }
            
            used.add(letter);
        }
        
        return used;
    }
    
    /**
     * Calculates score for a placed word.
     */
    public int calculateScore(int row, int col, boolean horizontal, String word) {
        int score = 0;
        int wordMultiplier = 1;
        
        for (int i = 0; i < word.length(); i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;
            
            char letter = Character.toUpperCase(word.charAt(i));
            int letterValue = getLetterValue(letter);
            
            PremiumSquare premium = getPremium(r, c);
            if (!isPremiumUsed(r, c) && premium != PremiumSquare.NORMAL) {
                letterValue *= premium.getLetterMultiplier();
                wordMultiplier *= premium.getWordMultiplier();
            }
            
            score += letterValue;
        }
        
        return score * wordMultiplier;
    }
    
    private int getLetterValue(char letter) {
        letter = Character.toUpperCase(letter);
        
        if ("AEILNORSTU".indexOf(letter) >= 0) return 1;
        if ("DG".indexOf(letter) >= 0) return 2;
        if ("BCMP".indexOf(letter) >= 0) return 3;
        if ("FHVWY".indexOf(letter) >= 0) return 4;
        if (letter == 'K') return 5;
        if ("JX".indexOf(letter) >= 0) return 8;
        if ("QZ".indexOf(letter) >= 0) return 10;
        
        return 0;
    }
    
    public void copyFrom(Board other) {
        if (other == null) return;
        
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                this.grid[r][c] = other.grid[r][c];
                this.usedPremium[r][c] = other.usedPremium[r][c];
                this.premiumGrid[r][c] = other.premiumGrid[r][c];
            }
        }
        this.boardName = other.boardName;
    }
    
    public void clear() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = '_';
                usedPremium[i][j] = false;
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board: ").append(boardName).append("\n");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sb.append(grid[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
