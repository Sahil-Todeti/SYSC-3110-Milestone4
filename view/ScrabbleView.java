package scrabble.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import scrabble.model.*;
import scrabble.util.GameSerializer;

/**
 * Main view for Scrabble game - manages all GUI components.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class ScrabbleView extends JFrame {
    
    private ScrabbleModel model;
    private BoardPanel boardPanel;
    private ControlPanel controlPanel;
    private TileRackPanel tileRackPanel;
    private JLabel currentPlayerLabel;
    private static final String SAVE_DIRECTORY = "saves";
    
    public ScrabbleView(ScrabbleModel model) {
        this.model = model;
        
        File savesDir = new File(SAVE_DIRECTORY);
        if (!savesDir.exists()) {
            savesDir.mkdir();
        }
        
        setTitle("Scrabble - Milestone 4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setResizable(true);
        
        createMenuBar();
        buildUI();
    }
    
    private void buildUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel with player info
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        currentPlayerLabel = new JLabel();
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(currentPlayerLabel, BorderLayout.WEST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Board in center
        boardPanel = new BoardPanel(model.getBoard());
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        
        // Control panel on right
        controlPanel = new ControlPanel(model, this);
        mainPanel.add(controlPanel, BorderLayout.EAST);
        
        // Tile rack at bottom
        tileRackPanel = new TileRackPanel(model);
        mainPanel.add(tileRackPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        newGameItem.addActionListener(e -> handleNewGame());
        fileMenu.add(newGameItem);
        
        fileMenu.addSeparator();
        
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        saveGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveGameItem.addActionListener(e -> handleSaveGame());
        fileMenu.add(saveGameItem);
        
        JMenuItem loadGameItem = new JMenuItem("Load Game");
        loadGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
        loadGameItem.addActionListener(e -> handleLoadGame());
        fileMenu.add(loadGameItem);
        
        fileMenu.addSeparator();
        
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Exit game?", "Exit", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);
        
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
    
    private void handleNewGame() {
        int result = JOptionPane.showConfirmDialog(this, "Start a new game?", "New Game", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION && model != null) {
            model.resetGame();
            updateDisplay();
            if (controlPanel != null) {
                controlPanel.clearLog();
                controlPanel.appendLog("New game started");
            }
        }
    }
    
    private void handleSaveGame() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String defaultName = "scrabble_" + timestamp + ".dat";
        
        JFileChooser fileChooser = new JFileChooser(new File(SAVE_DIRECTORY));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Scrabble Save Files (*.dat)", "dat"));
        fileChooser.setSelectedFile(new File(SAVE_DIRECTORY, defaultName));
        
        int result = fileChooser.showSaveDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();
                String filepath = selectedFile.getAbsolutePath();
                if (!filepath.endsWith(".dat")) {
                    filepath += ".dat";
                }
                
                GameSerializer serializer = new GameSerializer();
                serializer.saveGame(model, filepath);
                
                JOptionPane.showMessageDialog(this, "Game saved successfully!", "Save Game", JOptionPane.INFORMATION_MESSAGE);
                if (controlPanel != null) {
                    controlPanel.appendLog("Game saved");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void handleLoadGame() {
        JFileChooser fileChooser = new JFileChooser(new File(SAVE_DIRECTORY));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Scrabble Save Files (*.dat)", "dat"));
        
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();
                String filepath = selectedFile.getAbsolutePath();
                
                GameSerializer serializer = new GameSerializer();
                ScrabbleModel loadedModel = serializer.loadGame(filepath);
                
                if (loadedModel != null) {
                    this.model = loadedModel;
                    
                    if (boardPanel != null) {
                        boardPanel.setBoard(model.getBoard());
                        boardPanel.repaint();
                    }
                    
                    if (controlPanel != null) {
                        controlPanel.setModel(model);
                    }
                    
                    if (tileRackPanel != null) {
                        tileRackPanel.setModel(model);
                    }
                    
                    updateDisplay();
                    
                    JOptionPane.showMessageDialog(this, "Game loaded successfully!", "Load Game", JOptionPane.INFORMATION_MESSAGE);
                    if (controlPanel != null) {
                        controlPanel.appendLog("Game loaded");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to load game", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void updateDisplay() {
        if (model == null) return;
        
        Player currentPlayer = model.getCurrentPlayer();
        if (currentPlayer != null) {
            currentPlayerLabel.setText("Current Player: " + currentPlayer.getName());
        }
        
        if (boardPanel != null && model != null) {
            Board currentBoard = model.getBoard();
            if (currentBoard != null) {
                boardPanel.setBoard(currentBoard);
                boardPanel.repaint();
                boardPanel.revalidate();
                SwingUtilities.invokeLater(() -> boardPanel.repaint());
            }
        }
        
        if (tileRackPanel != null) {
            tileRackPanel.refresh();
        }
        
        if (controlPanel != null) {
            controlPanel.updateButtonStates();
            controlPanel.updateScores();
        }
    }
    
    public ScrabbleModel getModel() { return model; }
    public BoardPanel getBoardPanel() { return boardPanel; }
    public ControlPanel getControlPanel() { return controlPanel; }
}
