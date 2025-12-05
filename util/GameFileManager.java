package scrabble.util;

import java.io.*;
import scrabble.model.*;

/**
 * Handles game file save and load operations.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class GameFileManager {
    
    private static final String SAVE_DIR = "saves";
    private static final String FILE_EXTENSION = ".dat";
    
    /**
     * Saves the game state to a file.
     */
    public static boolean saveGame(ScrabbleModel model, String filename) {
        if (model == null || filename == null || filename.trim().isEmpty()) {
            return false;
        }
        
        File saveDirectory = new File(SAVE_DIR);
        if (!saveDirectory.exists()) {
            if (!saveDirectory.mkdir()) {
                return false;
            }
        }
        
        String filepath = SAVE_DIR + File.separator + filename + FILE_EXTENSION;
        File saveFile = new File(filepath);
        
        try (FileOutputStream fos = new FileOutputStream(saveFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            GameState state = new GameState(model);
            oos.writeObject(state);
            return true;
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Loads a game state from a file.
     */
    public static GameState loadGame(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return null;
        }
        
        String filepath = SAVE_DIR + File.separator + filename + FILE_EXTENSION;
        File saveFile = new File(filepath);
        
        if (!saveFile.exists()) {
            return null;
        }
        
        try (FileInputStream fis = new FileInputStream(saveFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            return (GameState) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Lists all available save files.
     */
    public static String[] listSaves() {
        File saveDirectory = new File(SAVE_DIR);
        if (!saveDirectory.exists() || !saveDirectory.isDirectory()) {
            return null;
        }
        
        File[] files = saveDirectory.listFiles((dir, name) -> name.endsWith(FILE_EXTENSION));
        if (files == null || files.length == 0) {
            return null;
        }
        
        String[] saveNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName();
            saveNames[i] = name.substring(0, name.length() - FILE_EXTENSION.length());
        }
        
        return saveNames;
    }
    
    /**
     * Deletes a save file.
     */
    public static boolean deleteSave(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return false;
        }
        
        String filepath = SAVE_DIR + File.separator + filename + FILE_EXTENSION;
        File saveFile = new File(filepath);
        
        if (!saveFile.exists()) {
            return false;
        }
        
        return saveFile.delete();
    }
}
