package scrabble.model;

import java.util.EventListener;

/**
 * Observer interface for game events.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public interface GameListener extends EventListener {
    
    /**
     * Called when game state changes.
     * 
     * @param event the game event
     */
    void gameStateChanged(GameEvent event);
}
