package scrabble;

import javax.swing.*;
import java.util.*;
import scrabble.model.*;
import scrabble.view.*;

/**
 * Main entry point for Scrabble Game.
 * Handles player setup, dictionary loading, board selection, and game initialization.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                // Select board configuration
                String[] boardOptions = {"Standard", "Diamond", "Cross"};
                int boardChoice = JOptionPane.showOptionDialog(
                    null,
                    "Select board configuration:\n\n" +
                    "- Standard (15x15 Classic Scrabble)\n" +
                    "- Diamond (15x15 Diamond Shape)\n" +
                    "- Cross (15x15 Cross Shape)",
                    "Scrabble - Board Selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    boardOptions,
                    boardOptions[0]
                );
                
                if (boardChoice == JOptionPane.CLOSED_OPTION) {
                    System.exit(0);
                }
                
                String selectedBoard = boardOptions[boardChoice];
                
                // Get number of players
                String input = JOptionPane.showInputDialog(
                    null,
                    "Enter number of players (2-4):",
                    "Scrabble - Player Count",
                    JOptionPane.PLAIN_MESSAGE
                );
                
                if (input == null) {
                    System.exit(0);
                }
                
                int numPlayers;
                try {
                    numPlayers = Integer.parseInt(input.trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                        "Invalid input. Must be a number between 2 and 4.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                    return;
                }
                
                if (numPlayers < 2 || numPlayers > 4) {
                    JOptionPane.showMessageDialog(null,
                        "Invalid number of players. Must be between 2 and 4.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                
                // Get player names
                List<String> playerNames = new ArrayList<>();
                for (int i = 1; i <= numPlayers; i++) {
                    String inputName = JOptionPane.showInputDialog(
                        null,
                        "Enter name for Player " + i + ":",
                        "Player " + i + " Setup",
                        JOptionPane.PLAIN_MESSAGE
                    );
                    
                    if (inputName == null) {
                        System.exit(0);
                    }
                    
                    String name = inputName.trim().isEmpty() ? "Player " + i : inputName.trim();
                    playerNames.add(name);
                }
                
                // Create game model
                BoardConfig boardConfig = new BoardConfig(selectedBoard);
                ScrabbleModel model = new ScrabbleModel(boardConfig, playerNames);
                
                // Load dictionary
                scrabble.model.Dictionary dictionary = new scrabble.model.Dictionary("dictionary.txt");
                model.setDictionary(dictionary);
                
                // Create and display view
                ScrabbleView view = new ScrabbleView(model);
                view.setVisible(true);
                model.startGame();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "Error starting game: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                System.exit(1);
            }
        });
    }
}
