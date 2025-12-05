package scrabble.view;

import javax.swing.*;
import java.awt.*;
import scrabble.model.*;

/**
 * Control panel with game actions, undo/redo, and game log.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class ControlPanel extends JPanel {
    
    private ScrabbleModel model;
    private ScrabbleView view;
    
    private JButton placeWordBtn;
    private JButton exchangeTilesBtn;
    private JButton passTurnBtn;
    private JButton undoBtn;
    private JButton redoBtn;
    
    private JTextArea gameLog;
    private JTextArea scoresDisplay;
    
    public ControlPanel(ScrabbleModel model, ScrabbleView view) {
        this.model = model;
        this.view = view;
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(240, 240, 240));
        setPreferredSize(new Dimension(350, 800));
        
        JPanel leftPanel = createLeftPanel();
        add(leftPanel, BorderLayout.WEST);
        
        JPanel rightPanel = createRightPanel();
        add(rightPanel, BorderLayout.CENTER);
        
        updateButtonStates();
        updateScores();
    }
    
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        actionsPanel.setMaximumSize(new Dimension(200, 140));
        actionsPanel.setBackground(Color.WHITE);
        
        placeWordBtn = new JButton("Place Word");
        placeWordBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        placeWordBtn.setMaximumSize(new Dimension(180, 35));
        placeWordBtn.addActionListener(e -> handlePlaceWord());
        actionsPanel.add(placeWordBtn);
        actionsPanel.add(Box.createVerticalStrut(5));
        
        exchangeTilesBtn = new JButton("Exchange Tiles");
        exchangeTilesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exchangeTilesBtn.setMaximumSize(new Dimension(180, 35));
        exchangeTilesBtn.addActionListener(e -> handleExchangeTiles());
        actionsPanel.add(exchangeTilesBtn);
        actionsPanel.add(Box.createVerticalStrut(5));
        
        passTurnBtn = new JButton("Pass Turn");
        passTurnBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        passTurnBtn.setMaximumSize(new Dimension(180, 35));
        passTurnBtn.addActionListener(e -> handlePassTurn());
        actionsPanel.add(passTurnBtn);
        
        panel.add(actionsPanel);
        panel.add(Box.createVerticalStrut(10));
        
        JPanel undoRedoPanel = new JPanel();
        undoRedoPanel.setLayout(new BoxLayout(undoRedoPanel, BoxLayout.Y_AXIS));
        undoRedoPanel.setBorder(BorderFactory.createTitledBorder("Undo/Redo"));
        undoRedoPanel.setMaximumSize(new Dimension(200, 95));
        undoRedoPanel.setBackground(Color.WHITE);
        
        undoBtn = new JButton("<< Undo");
        undoBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        undoBtn.setMaximumSize(new Dimension(180, 35));
        undoBtn.setEnabled(false);
        undoBtn.addActionListener(e -> handleUndo());
        undoRedoPanel.add(undoBtn);
        undoRedoPanel.add(Box.createVerticalStrut(5));
        
        redoBtn = new JButton("Redo >>");
        redoBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        redoBtn.setMaximumSize(new Dimension(180, 35));
        redoBtn.setEnabled(false);
        redoBtn.addActionListener(e -> handleRedo());
        undoRedoPanel.add(redoBtn);
        
        panel.add(undoRedoPanel);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 10));
        
        scoresDisplay = new JTextArea(8, 25);
        scoresDisplay.setEditable(false);
        scoresDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scoresDisplay.setBorder(BorderFactory.createTitledBorder("Scores"));
        scoresDisplay.setBackground(Color.WHITE);
        JScrollPane scoresScroll = new JScrollPane(scoresDisplay);
        scoresScroll.setPreferredSize(new Dimension(250, 150));
        panel.add(scoresScroll, BorderLayout.NORTH);
        
        gameLog = new JTextArea(15, 25);
        gameLog.setEditable(false);
        gameLog.setFont(new Font("Monospaced", Font.PLAIN, 10));
        gameLog.setBorder(BorderFactory.createTitledBorder("Game Log"));
        gameLog.setBackground(Color.WHITE);
        gameLog.setLineWrap(true);
        gameLog.setWrapStyleWord(true);
        gameLog.setText("Game initialized.\n");
        JScrollPane logScroll = new JScrollPane(gameLog);
        panel.add(logScroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void handlePlaceWord() {
        if (model == null) {
            JOptionPane.showMessageDialog(this, "No game loaded!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        Player currentPlayer = model.getCurrentPlayer();
        String playerTiles = currentPlayer != null ? currentPlayer.getRackDisplay() : "No tiles";
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel tilesLabel = new JLabel("Your Tiles: " + playerTiles);
        tilesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tilesLabel.setForeground(new Color(0, 100, 0));
        inputPanel.add(tilesLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Word:"), gbc);
        JTextField wordField = new JTextField("", 12);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(wordField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("Row (0-14):"), gbc);
        JTextField rowField = new JTextField("7", 12);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(rowField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("Column (0-14):"), gbc);
        JTextField colField = new JTextField("7", 12);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(colField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("Direction:"), gbc);
        JComboBox<String> directionBox = new JComboBox<>(new String[]{"Horizontal", "Vertical"});
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(directionBox, gbc);
        
        if (model.getBoard().isEmpty()) {
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 2;
            JLabel hintLabel = new JLabel("First word must cover center (7,7)");
            hintLabel.setForeground(Color.BLUE);
            inputPanel.add(hintLabel, gbc);
        }
        
        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Place Word", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String word = wordField.getText().trim().toUpperCase();
            String rowText = rowField.getText().trim();
            String colText = colField.getText().trim();
            boolean horizontal = (directionBox.getSelectedIndex() == 0);
            
            if (word.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Word cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!word.matches("[A-Z]+")) {
                JOptionPane.showMessageDialog(this, "Word must contain only letters!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (rowText.isEmpty() || colText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Row and Column required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                int row = Integer.parseInt(rowText);
                int col = Integer.parseInt(colText);
                
                if (row < 0 || row > 14 || col < 0 || col > 14) {
                    JOptionPane.showMessageDialog(this, "Row and Column must be 0-14!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                boolean success = model.placeWord(row, col, horizontal, word);
                
                if (!success) {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to place word. Check:\n" +
                        "- Word is valid in dictionary\n" +
                        "- First move uses center square (7,7)\n" +
                        "- Word connects to existing tiles", 
                        "Placement Failed", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                view.updateDisplay();
                
                BoardPanel boardPanel = view.getBoardPanel();
                if (boardPanel != null) {
                    Board currentBoard = model.getBoard();
                    if (currentBoard != null) {
                        boardPanel.setBoard(currentBoard);
                        boardPanel.repaint();
                        boardPanel.revalidate();
                        boardPanel.refreshBoard();
                    }
                }
                
                updateButtonStates();
                updateScores();
                
                String dir = horizontal ? "Horizontal" : "Vertical";
                appendLog("Placed '" + word + "' at (" + row + "," + col + ") " + dir);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Row and Column must be valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void handleExchangeTiles() {
        if (model == null) return;
        model.passTurn();
        view.updateDisplay();
        updateButtonStates();
        updateScores();
        appendLog("Tiles exchanged");
    }
    
    private void handlePassTurn() {
        if (model == null) return;
        model.passTurn();
        view.updateDisplay();
        updateButtonStates();
        updateScores();
        appendLog("Turn passed");
    }
    
    private void handleUndo() {
        if (model == null || !model.canUndo()) return;
        model.undo();
        view.updateDisplay();
        updateButtonStates();
        updateScores();
        appendLog("Undo executed");
    }
    
    private void handleRedo() {
        if (model == null || !model.canRedo()) return;
        model.redo();
        view.updateDisplay();
        updateButtonStates();
        updateScores();
        appendLog("Redo executed");
    }
    
    public void updateButtonStates() {
        if (model == null) {
            undoBtn.setEnabled(false);
            redoBtn.setEnabled(false);
            placeWordBtn.setEnabled(false);
            exchangeTilesBtn.setEnabled(false);
            passTurnBtn.setEnabled(false);
            return;
        }
        
        undoBtn.setEnabled(model.canUndo());
        redoBtn.setEnabled(model.canRedo());
        placeWordBtn.setEnabled(true);
        exchangeTilesBtn.setEnabled(true);
        passTurnBtn.setEnabled(true);
    }
    
    public void updateScores() {
        if (model == null) return;
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== CURRENT PLAYER ===\n");
        Player currentPlayer = model.getCurrentPlayer();
        if (currentPlayer != null) {
            sb.append(currentPlayer.getName()).append("\n");
            sb.append("Tiles: ").append(currentPlayer.getRackDisplay()).append("\n");
            sb.append("Score: ").append(currentPlayer.getScore()).append(" pts\n");
        }
        sb.append("\n=== ALL SCORES ===\n\n");
        int i = 1;
        for (Player player : model.getPlayers()) {
            sb.append(i).append(". ").append(player.getName()).append("\n");
            sb.append("   ").append(player.getScore()).append(" pts\n");
            if (player == model.getCurrentPlayer()) {
                sb.append("   (Current)\n");
            }
            sb.append("\n");
            i++;
        }
        
        scoresDisplay.setText(sb.toString());
    }
    
    public void appendLog(String message) {
        gameLog.append(message + "\n");
        gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }
    
    public void clearLog() {
        gameLog.setText("");
    }
    
    public void setModel(ScrabbleModel newModel) {
        this.model = newModel;
        updateButtonStates();
        updateScores();
        clearLog();
        appendLog("Game loaded.");
    }
    
    public ScrabbleModel getModel() {
        return model;
    }
}
