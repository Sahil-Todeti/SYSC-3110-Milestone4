package scrabble.util;

import java.io.*;
import scrabble.model.*;

/**
 * Handles serialization and deserialization of game state.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class GameSerializer {
    
    private static final String FILE_EXTENSION = ".dat";
    private static final String MAGIC_NUMBER = "SCRABBLE_M4";
    private static final int VERSION = 1;
    
    /**
     * Saves the game to a file.
     * 
     * @param model the game model to save
     * @param filename the file path
     * @return true if successful
     */
    public boolean saveGame(ScrabbleModel model, String filename) {
        if (model == null || filename == null) {
            return false;
        }
        
        String filepath = filename.endsWith(FILE_EXTENSION) ? filename : filename + FILE_EXTENSION;
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeUTF(MAGIC_NUMBER);
            oos.writeInt(VERSION);
            oos.writeLong(System.currentTimeMillis());
            oos.writeObject(model);
            oos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Loads a game from a file.
     * 
     * @param filename the file path
     * @return the loaded model, or null if failed
     */
    public ScrabbleModel loadGame(String filename) {
        if (filename == null) {
            return null;
        }
        
        String filepath = filename.endsWith(FILE_EXTENSION) ? filename : filename + FILE_EXTENSION;
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            String magic = ois.readUTF();
            if (!magic.equals(MAGIC_NUMBER)) {
                return null;
            }
            
            int version = ois.readInt();
            if (version != VERSION) {
                return null;
            }
            
            long timestamp = ois.readLong();
            return (ScrabbleModel) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Checks if a save file is valid.
     */
    public boolean isSaveFileValid(String filename) {
        if (filename == null) {
            return false;
        }
        
        String filepath = filename.endsWith(FILE_EXTENSION) ? filename : filename + FILE_EXTENSION;
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            String magic = ois.readUTF();
            int version = ois.readInt();
            return magic.equals(MAGIC_NUMBER) && version == VERSION;
        } catch (Exception e) {
            return false;
        }
    }
}

