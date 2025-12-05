package scrabble.model;

import java.io.Serializable;
import java.util.*;

/**
 * Captures complete game state for undo/redo functionality.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class GameState implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final char[][] boardState;
    private final List<Player> players;
    private final int currentPlayerIndex;
    private final int consecutivePasses;
    private final long timestamp;
    
    public GameState(ScrabbleModel model) {
        if (model == null) {
            throw new IllegalArgumentException("ScrabbleModel cannot be null");
        }
        
        this.boardState = captureBoard(model.getBoard());
        this.players = deepCopyPlayers(model.getPlayers());
        this.currentPlayerIndex = model.getCurrentPlayerIndex();
        this.consecutivePasses = model.getConsecutivePasses();
        this.timestamp = System.currentTimeMillis();
    }
    
    private char[][] captureBoard(Board board) {
        char[][] snapshot = new char[15][15];
        
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                snapshot[i][j] = '_';
            }
        }
        
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                Character tile = board.get(row, col);
                if (tile != null && tile != '_' && tile != ' ' && tile != 0) {
                    snapshot[row][col] = tile;
                }
            }
        }
        
        return snapshot;
    }
    
    private List<Player> deepCopyPlayers(List<Player> originalPlayers) {
        if (originalPlayers == null || originalPlayers.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Player> playerCopies = new ArrayList<>();
        
        for (Player original : originalPlayers) {
            Player copy = new Player(original.getName());
            copy.setScore(original.getScore());
            List<Character> rackCopy = new ArrayList<>(original.getRack());
            copy.setTiles(rackCopy);
            playerCopies.add(copy);
        }
        
        return playerCopies;
    }
    
    public char[][] getBoardState() { return boardState; }
    public List<Player> getPlayers() { return new ArrayList<>(players); }
    public int getCurrentPlayerIndex() { return currentPlayerIndex; }
    public int getConsecutivePasses() { return consecutivePasses; }
    public long getTimestamp() { return timestamp; }
    
    @Override
    public String toString() {
        return String.format("GameState[players=%d, time=%s]",
            players.size(), new java.util.Date(timestamp));
    }
}
