package scrabble.model;

import java.util.*;

/**
 * Manages undo/redo functionality using two stacks.
 *
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class UndoRedoManager {
    
    private LinkedList<GameState> undoStack;
    private LinkedList<GameState> redoStack;
    private static final int MAX_HISTORY = 100;

    /**
     * Instantiates a new Undo redo manager.
     */
    public UndoRedoManager() {
        this.undoStack = new LinkedList<>();
        this.redoStack = new LinkedList<>();
    }

    /**
     * Saves current state to undo stack.
     *
     * @param state the state
     */
    public void saveState(GameState state) {
        if (state == null) return;
        
        undoStack.push(state);
        redoStack.clear();

        if (undoStack.size() > MAX_HISTORY) {
            undoStack.removeLast();
        }
    }

    /**
     * Undoes last action.
     *
     * @param currentModel the current model
     * @return the game state
     */
    public GameState undo(ScrabbleModel currentModel) {
        if (!canUndo()) return null;
        
        if (currentModel != null) {
            GameState currentState = new GameState(currentModel);
            redoStack.push(currentState);
        }
        
        return undoStack.pop();
    }

    /**
     * Redoes last undone action.
     *
     * @param currentModel the current model
     * @return the game state
     */
    public GameState redo(ScrabbleModel currentModel) {
        if (!canRedo()) return null;
        
        if (currentModel != null) {
            GameState currentState = new GameState(currentModel);
            undoStack.push(currentState);
        }
        
        return redoStack.pop();
    }

    /**
     * Can undo boolean.
     *
     * @return the boolean
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * Can redo boolean.
     *
     * @return the boolean
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    /**
     * Clear.
     */
    public void clear() {
            undoStack.clear();
            redoStack.clear();
    }

    /**
     * Gets undo depth.
     *
     * @return the undo depth
     */
    public int getUndoDepth() {
        return undoStack.size();
    }

    /**
     * Gets redo depth.
     *
     * @return the redo depth
     */
    public int getRedoDepth() {
        return redoStack.size();
    }
    
    @Override
    public String toString() {
        return String.format("UndoRedoManager[undo=%d, redo=%d]",
            undoStack.size(), redoStack.size());
    }
}
