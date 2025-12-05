package scrabble.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import scrabble.model.Player;
import scrabble.model.ScrabbleModel;

/**
 * Displays the current player's tiles at the bottom of the board.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class TileRackPanel extends JPanel {
    
    private ScrabbleModel model;
    private static final int TILE_SIZE = 50;
    private static final int TILE_SPACING = 8;
    
    public TileRackPanel(ScrabbleModel model) {
        this.model = model;
        setPreferredSize(new Dimension(800, 100));
        setBackground(new Color(139, 90, 43));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }
    
    public void setModel(ScrabbleModel newModel) {
        this.model = newModel;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (model == null) return;
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Player currentPlayer = model.getCurrentPlayer();
        if (currentPlayer == null) return;
        
        List<Character> tiles = currentPlayer.getRack();
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        String label = currentPlayer.getName() + "'s Tiles:";
        g2d.drawString(label, 15, 25);
        
        int totalWidth = tiles.size() * (TILE_SIZE + TILE_SPACING) - TILE_SPACING;
        int startX = (getWidth() - totalWidth) / 2;
        int startY = 35;
        
        for (int i = 0; i < tiles.size(); i++) {
            Character tile = tiles.get(i);
            int x = startX + i * (TILE_SIZE + TILE_SPACING);
            drawTile(g2d, x, startY, tile);
        }
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        String countText = "(" + tiles.size() + " tiles)";
        g2d.drawString(countText, getWidth() - 80, 25);
    }
    
    private void drawTile(Graphics2D g2d, int x, int y, Character tile) {
        g2d.setColor(new Color(255, 248, 220));
        g2d.fillRoundRect(x, y, TILE_SIZE, TILE_SIZE, 8, 8);
        
        g2d.setColor(new Color(101, 67, 33));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x, y, TILE_SIZE, TILE_SIZE, 8, 8);
        
        g2d.setColor(new Color(210, 180, 140));
        g2d.drawLine(x + 3, y + TILE_SIZE - 3, x + TILE_SIZE - 3, y + TILE_SIZE - 3);
        g2d.drawLine(x + TILE_SIZE - 3, y + 3, x + TILE_SIZE - 3, y + TILE_SIZE - 3);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 28));
        FontMetrics fm = g2d.getFontMetrics();
        String letter = String.valueOf(tile);
        int letterX = x + (TILE_SIZE - fm.stringWidth(letter)) / 2;
        int letterY = y + ((TILE_SIZE - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(letter, letterX, letterY);
        
        int points = getLetterPoints(tile);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.drawString(String.valueOf(points), x + TILE_SIZE - 14, y + TILE_SIZE - 5);
    }
    
    private int getLetterPoints(char letter) {
        letter = Character.toUpperCase(letter);
        if ("AEILNORSTU".indexOf(letter) >= 0) return 1;
        if ("DG".indexOf(letter) >= 0) return 2;
        if ("BCMP".indexOf(letter) >= 0) return 3;
        if ("FHVWY".indexOf(letter) >= 0) return 4;
        if (letter == 'K') return 5;
        if ("JX".indexOf(letter) >= 0) return 8;
        if ("QZ".indexOf(letter) >= 0) return 10;
        return 0;
    }
    
    public void refresh() {
        repaint();
    }
}
