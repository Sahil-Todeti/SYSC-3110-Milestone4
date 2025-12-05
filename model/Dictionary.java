package scrabble.model;

import java.io.*;
import java.util.*;

/**
 * Loads and manages valid Scrabble words from a dictionary file.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class Dictionary implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final Set<String> validWords;

    public Dictionary(String filePath) {
        validWords = new HashSet<>();
        loadDictionary(filePath);
    }

    private void loadDictionary(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                word = word.trim().toUpperCase();
                if (word.length() > 1 && word.matches("^[A-Z]+$")) {
                    validWords.add(word);
                }
            }
        } catch (IOException e) {
            // Dictionary file not found - continue without validation
        }
    }

    public boolean isValid(String word) {
        if (word == null) return false;
        return validWords.contains(word.toUpperCase());
    }
    
    public boolean isValidWord(String word) {
        return isValid(word);
    }

    public int size() {
        return validWords.size();
    }

    public boolean isEmpty() {
        return validWords.isEmpty();
    }
}
