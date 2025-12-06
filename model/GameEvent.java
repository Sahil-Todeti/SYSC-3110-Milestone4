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

    /**
     * The enum Type.
     */
    public enum Type implements Serializable {
        /**
         * The Game started.
         */
        GAME_STARTED("Game Started"),
        /**
         * The Board updated.
         */
        BOARD_UPDATED("Board Updated"),
        /**
         * The Turn changed.
         */
        TURN_CHANGED("Turn Changed"),
        /**
         * The Score updated.
         */
        SCORE_UPDATED("Score Updated"),
        /**
         * The Rack updated.
         */
        RACK_UPDATED("Rack Updated"),
        /**
         * The Player updated.
         */
        PLAYER_UPDATED("Player Updated"),
        /**
         * The Invalid move.
         */
        INVALID_MOVE("Invalid Move"),
        /**
         * The Game ended.
         */
        GAME_ENDED("Game Ended");
        
        private final String description;
        
        Type(String description) {
            this.description = description;
        }

        /**
         * Gets description.
         *
         * @return the description
         */
        public String getDescription() {
            return description;
        }
    }
    
    private final Type type;
    private final String message;
    private final Object data;
    private final long timestamp;

    /**
     * Instantiates a new Game event.
     *
     * @param source  the source
     * @param type    the type
     * @param message the message
     */
    public GameEvent(Object source, Type type, String message) {
        this(source, type, message, null);
    }

    /**
     * Instantiates a new Game event.
     *
     * @param source  the source
     * @param type    the type
     * @param message the message
     * @param data    the data
     */
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

    /**
     * Gets type.
     *
     * @return the type
     */
    public Type getType() { return type; }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() { return message; }

    /**
     * Gets data.
     *
     * @return the data
     */
    public Object getData() { return data; }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public long getTimestamp() { return timestamp; }
    
    @Override
    public String toString() {
        return String.format("GameEvent [type=%s, message=%s]", type.getDescription(), message);
    }
}
