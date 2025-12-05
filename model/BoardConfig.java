package scrabble.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a board configuration for Scrabble.
 * Supports custom board types (Standard, Diamond, Cross).
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class BoardConfig {
    
    private String name;
    private int size;
    private List<int[]> tripleWordPositions;
    private List<int[]> doubleWordPositions;
    private List<int[]> tripleLetterPositions;
    private List<int[]> doubleLetterPositions;
    
    public BoardConfig(String name) {
        this.name = name != null ? name : "Standard";
        this.size = 15;
        initializePremiums();
    }
    
    public BoardConfig(String name, int size) {
        this.name = name != null ? name : "Standard";
        this.size = size > 0 ? size : 15;
        initializePremiums();
    }
    
    private void initializePremiums() {
        tripleWordPositions = new ArrayList<>();
        doubleWordPositions = new ArrayList<>();
        tripleLetterPositions = new ArrayList<>();
        doubleLetterPositions = new ArrayList<>();
        
        if (name.equalsIgnoreCase("Diamond")) {
            initializeDiamondBoard();
        } else if (name.equalsIgnoreCase("Cross")) {
            initializeCrossBoard();
        } else {
            initializeStandardBoard();
        }
    }
    
    private void initializeStandardBoard() {
        // Triple Word Scores
        tripleWordPositions.add(new int[]{0, 0});
        tripleWordPositions.add(new int[]{0, 7});
        tripleWordPositions.add(new int[]{0, 14});
        tripleWordPositions.add(new int[]{7, 0});
        tripleWordPositions.add(new int[]{7, 14});
        tripleWordPositions.add(new int[]{14, 0});
        tripleWordPositions.add(new int[]{14, 7});
        tripleWordPositions.add(new int[]{14, 14});
        
        // Double Word Scores
        for (int i = 1; i <= 4; i++) {
            doubleWordPositions.add(new int[]{i, i});
            doubleWordPositions.add(new int[]{i, 14 - i});
            doubleWordPositions.add(new int[]{14 - i, i});
            doubleWordPositions.add(new int[]{14 - i, 14 - i});
        }
        
        // Triple Letter Scores
        tripleLetterPositions.add(new int[]{1, 5});
        tripleLetterPositions.add(new int[]{1, 9});
        tripleLetterPositions.add(new int[]{5, 1});
        tripleLetterPositions.add(new int[]{5, 5});
        tripleLetterPositions.add(new int[]{5, 9});
        tripleLetterPositions.add(new int[]{5, 13});
        tripleLetterPositions.add(new int[]{9, 1});
        tripleLetterPositions.add(new int[]{9, 5});
        tripleLetterPositions.add(new int[]{9, 9});
        tripleLetterPositions.add(new int[]{9, 13});
        tripleLetterPositions.add(new int[]{13, 5});
        tripleLetterPositions.add(new int[]{13, 9});
        
        // Double Letter Scores
        doubleLetterPositions.add(new int[]{0, 3});
        doubleLetterPositions.add(new int[]{0, 11});
        doubleLetterPositions.add(new int[]{2, 6});
        doubleLetterPositions.add(new int[]{2, 8});
        doubleLetterPositions.add(new int[]{3, 0});
        doubleLetterPositions.add(new int[]{3, 7});
        doubleLetterPositions.add(new int[]{3, 14});
        doubleLetterPositions.add(new int[]{6, 2});
        doubleLetterPositions.add(new int[]{6, 6});
        doubleLetterPositions.add(new int[]{6, 8});
        doubleLetterPositions.add(new int[]{6, 12});
        doubleLetterPositions.add(new int[]{7, 3});
        doubleLetterPositions.add(new int[]{7, 11});
        doubleLetterPositions.add(new int[]{8, 2});
        doubleLetterPositions.add(new int[]{8, 6});
        doubleLetterPositions.add(new int[]{8, 8});
        doubleLetterPositions.add(new int[]{8, 12});
        doubleLetterPositions.add(new int[]{11, 0});
        doubleLetterPositions.add(new int[]{11, 7});
        doubleLetterPositions.add(new int[]{11, 14});
        doubleLetterPositions.add(new int[]{12, 6});
        doubleLetterPositions.add(new int[]{12, 8});
        doubleLetterPositions.add(new int[]{14, 3});
        doubleLetterPositions.add(new int[]{14, 11});
    }
    
    private void initializeDiamondBoard() {
        tripleWordPositions.add(new int[]{0, 7});
        tripleWordPositions.add(new int[]{7, 0});
        tripleWordPositions.add(new int[]{7, 14});
        tripleWordPositions.add(new int[]{14, 7});
        
        doubleWordPositions.add(new int[]{2, 7});
        doubleWordPositions.add(new int[]{7, 2});
        doubleWordPositions.add(new int[]{7, 12});
        doubleWordPositions.add(new int[]{12, 7});
        
        tripleLetterPositions.add(new int[]{3, 3});
        tripleLetterPositions.add(new int[]{3, 11});
        tripleLetterPositions.add(new int[]{11, 3});
        tripleLetterPositions.add(new int[]{11, 11});
        
        doubleLetterPositions.add(new int[]{4, 7});
        doubleLetterPositions.add(new int[]{7, 4});
        doubleLetterPositions.add(new int[]{7, 10});
        doubleLetterPositions.add(new int[]{10, 7});
    }
    
    private void initializeCrossBoard() {
        tripleWordPositions.add(new int[]{0, 7});
        tripleWordPositions.add(new int[]{7, 0});
        tripleWordPositions.add(new int[]{7, 14});
        tripleWordPositions.add(new int[]{14, 7});
        
        doubleWordPositions.add(new int[]{3, 7});
        doubleWordPositions.add(new int[]{7, 3});
        doubleWordPositions.add(new int[]{7, 11});
        doubleWordPositions.add(new int[]{11, 7});
        
        tripleLetterPositions.add(new int[]{1, 7});
        tripleLetterPositions.add(new int[]{7, 1});
        tripleLetterPositions.add(new int[]{7, 13});
        tripleLetterPositions.add(new int[]{13, 7});
        
        doubleLetterPositions.add(new int[]{5, 7});
        doubleLetterPositions.add(new int[]{7, 5});
        doubleLetterPositions.add(new int[]{7, 9});
        doubleLetterPositions.add(new int[]{9, 7});
    }
    
    public String getName() { return name; }
    public int getSize() { return size; }
    public List<int[]> getTripleWordPositions() { return tripleWordPositions; }
    public List<int[]> getDoubleWordPositions() { return doubleWordPositions; }
    public List<int[]> getTripleLetterPositions() { return tripleLetterPositions; }
    public List<int[]> getDoubleLetterPositions() { return doubleLetterPositions; }
    
    public static List<BoardConfig> getAllBoards() {
        List<BoardConfig> boards = new ArrayList<>();
        boards.add(new BoardConfig("Standard", 15));
        boards.add(new BoardConfig("Diamond", 15));
        boards.add(new BoardConfig("Cross", 15));
        return boards;
    }
    
    @Override
    public String toString() {
        return name + " (" + size + "x" + size + ")";
    }
}
