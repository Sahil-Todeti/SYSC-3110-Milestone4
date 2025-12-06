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
    
    public UndoRedoManager() {
        this.undoStack = new LinkedList<>();
        this.redoStack = new LinkedList<>();
    }
    
    /**
     * Saves current state to undo stack.
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
     */
    public GameState redo(ScrabbleModel currentModel) {
        if (!canRedo()) return null;
        
        if (currentModel != null) {
            GameState currentState = new GameState(currentModel);
            undoStack.push(currentState);
        }
        
        return redoStack.pop();
    }
    
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    
    public void clear() {
            undoStack.clear();
            redoStack.clear();
    }
    
    public int getUndoDepth() {
        return undoStack.size();
    }
    
    public int getRedoDepth() {
        return redoStack.size();
    }
    
    @Override
    public String toString() {
        return String.format("UndoRedoManager[undo=%d, redo=%d]",
            undoStack.size(), redoStack.size());
    }
}
