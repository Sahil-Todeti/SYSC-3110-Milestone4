package scrabble.util;

import java.io.*;
import java.util.*;
import scrabble.model.*;

/**
 * Loads custom board configurations from JSON files.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class BoardLoader {
    
    private static final String BOARDS_DIRECTORY = "boards";
    private static final String FILE_EXTENSION = ".json";
    
    /**
     * Loads a board configuration by name.
     */
    public static BoardConfig loadBoard(String boardName) {
        if (boardName == null || boardName.isEmpty()) {
            return new BoardConfig("Standard");
        }
        
        String filename = BOARDS_DIRECTORY + File.separator + boardName + FILE_EXTENSION;
        
        try {
            File file = new File(filename);
            if (file.exists()) {
                return parseJsonFile(file, boardName);
            }
        } catch (Exception e) {
            // Fall back to predefined board
        }
        
        return new BoardConfig(boardName);
    }
    
    private static BoardConfig parseJsonFile(File file, String boardName) throws IOException {
        StringBuilder content = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line.trim());
            }
        }
        
        return parseJsonContent(content.toString(), boardName);
    }
    
    private static BoardConfig parseJsonContent(String json, String boardName) {
        BoardConfig config = new BoardConfig(boardName);
        
        config.getTripleWordPositions().clear();
        config.getDoubleWordPositions().clear();
        config.getTripleLetterPositions().clear();
        config.getDoubleLetterPositions().clear();
        
        extractPositions(json, "tripleWord", config.getTripleWordPositions());
        extractPositions(json, "doubleWord", config.getDoubleWordPositions());
        extractPositions(json, "tripleLetter", config.getTripleLetterPositions());
        extractPositions(json, "doubleLetter", config.getDoubleLetterPositions());
        
        return config;
    }
    
    private static void extractPositions(String json, String key, List<int[]> list) {
        try {
            String searchKey = "\"" + key + "\":";
            int startIdx = json.indexOf(searchKey);
            if (startIdx == -1) return;
            
            startIdx = json.indexOf('[', startIdx);
            int endIdx = json.indexOf(']', startIdx);
            
            if (startIdx == -1 || endIdx == -1) return;
            
            String content = json.substring(startIdx + 1, endIdx);
            String[] pairs = content.split("],\\s*\\[");
            
            for (String pair : pairs) {
                pair = pair.replace("[", "").replace("]", "").trim();
                String[] coords = pair.split(",");
                
                if (coords.length == 2) {
                    int row = Integer.parseInt(coords[0].trim());
                    int col = Integer.parseInt(coords[1].trim());
                    list.add(new int[]{row, col});
                }
            }
        } catch (Exception e) {
            // Ignore parsing errors
        }
    }
    
    public static BoardConfig createStandardBoard() {
        return new BoardConfig("Standard");
    }
    
    public static BoardConfig createDiamondBoard() {
        return new BoardConfig("Diamond");
    }
    
    public static BoardConfig createCrossBoard() {
        return new BoardConfig("Cross");
    }
    
    public static List<String> getAvailableBoards() {
        List<String> boards = new ArrayList<>();
        boards.add("Standard");
        boards.add("Diamond");
        boards.add("Cross");
        return boards;
    }
}
