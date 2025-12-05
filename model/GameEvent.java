package scrabble.model;

import java.io.Serializable;
import java.util.EventObject;

/**
 * Represents a game event in Scrabble.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class GameEvent extends EventObject implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public enum Type implements Serializable {
        GAME_STARTED("Game Started"),
        BOARD_UPDATED("Board Updated"),
        TURN_CHANGED("Turn Changed"),
        SCORE_UPDATED("Score Updated"),
        RACK_UPDATED("Rack Updated"),
        PLAYER_UPDATED("Player Updated"),
        INVALID_MOVE("Invalid Move"),
        GAME_ENDED("Game Ended");
        
        private final String description;
        
        Type(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    private final Type type;
    private final String message;
    private final Object data;
    private final long timestamp;
    
    public GameEvent(Object source, Type type, String message) {
        this(source, type, message, null);
    }
    
    public GameEvent(Object source, Type type, String message, Object data) {
        super(source);
        
        if (type == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }
        
        this.type = type;
        this.message = message != null ? message : "";
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
    
    public Type getType() { return type; }
    public String getMessage() { return message; }
    public Object getData() { return data; }
    public long getTimestamp() { return timestamp; }
    
    @Override
    public String toString() {
        return String.format("GameEvent [type=%s, message=%s]", type.getDescription(), message);
    }
}
