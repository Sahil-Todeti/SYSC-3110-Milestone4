package scrabble.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Panel displaying player's tile rack with selection capability.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class RackPanel extends JPanel {
    
    private List<Character> tiles;
    private Set<Integer> selectedIndices;
    private static final int TILE_SIZE = 60;
    private static final int SPACING = 10;
    
    public RackPanel() {
        this.tiles = new ArrayList<>();
        this.selectedIndices = new HashSet<>();
        setPreferredSize(new Dimension(800, 90));
        setBackground(new Color(139, 69, 19));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            "Your Tiles (Click to select for exchange)",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            Color.WHITE
        ));
        
        addMouseListener(new TileSelectionListener());
    }
    
    public void setTiles(List<Character> tiles) {
        this.tiles = new ArrayList<>(tiles);
        this.selectedIndices.clear();
        repaint();
    }
    
    public List<Character> getSelectedTiles() {
        List<Character> selected = new ArrayList<>();
        for (int index : selectedIndices) {
            if (index >= 0 && index < tiles.size()) {
                selected.add(tiles.get(index));
            }
        }
        return selected;
    }
    
    public void clearSelection() {
        selectedIndices.clear();
        repaint();
    }
    
    public void refreshRack() {
        repaint();
    }
    
    private int getTileAtPosition(int x, int y) {
        if (tiles.isEmpty()) {
            return -1;
        }
        
        int startX = (getWidth() - tiles.size() * (TILE_SIZE + SPACING)) / 2;
        
        for (int i = 0; i < tiles.size(); i++) {
            int tileX = startX + i * (TILE_SIZE + SPACING);
            int tileY = 15;
            
            if (x >= tileX && x <= tileX + TILE_SIZE &&
                y >= tileY && y <= tileY + TILE_SIZE) {
                return i;
            }
        }
        
        return -1;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (tiles.isEmpty()) {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setFont(new Font("Arial", Font.ITALIC, 14));
            g2d.drawString("No tiles remaining", 20, 50);
            return;
        }
        
        int startX = (getWidth() - tiles.size() * (TILE_SIZE + SPACING)) / 2;
        
        for (int i = 0; i < tiles.size(); i++) {
            int x = startX + i * (TILE_SIZE + SPACING);
            int y = 15;
            
            boolean isSelected = selectedIndices.contains(i);
            drawTileInRack(g2d, x, y, tiles.get(i), isSelected);
        }
    }
    
    private void drawTileInRack(Graphics2D g2d, int x, int y, Character tile, boolean isSelected) {
        if (isSelected) {
            g2d.setColor(new Color(100, 180, 255));
        } else {
            g2d.setColor(new Color(255, 248, 220));
        }
        g2d.fillRoundRect(x, y, TILE_SIZE, TILE_SIZE, 10, 10);
        
        g2d.setColor(Color.BLACK);
        if (isSelected) {
            g2d.setStroke(new BasicStroke(3));
        } else {
            g2d.setStroke(new BasicStroke(2));
        }
        g2d.drawRoundRect(x, y, TILE_SIZE, TILE_SIZE, 10, 10);
        g2d.setStroke(new BasicStroke(1));
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 32));
        String letter = String.valueOf(tile);
        FontMetrics fm = g2d.getFontMetrics();
        int letterX = x + (TILE_SIZE - fm.stringWidth(letter)) / 2;
        int letterY = y + (TILE_SIZE + fm.getAscent()) / 2 - 5;
        g2d.drawString(letter, letterX, letterY);
        
        if (isSelected) {
            g2d.setColor(new Color(0, 150, 0));
            g2d.fillOval(x + TILE_SIZE - 15, y + TILE_SIZE - 15, 15, 15);
        }
    }
    
    private class TileSelectionListener implements MouseListener {
        
        @Override
        public void mouseClicked(MouseEvent e) {
            int index = getTileAtPosition(e.getX(), e.getY());
            
            if (index >= 0) {
                if (selectedIndices.contains(index)) {
                    selectedIndices.remove(index);
                } else {
                    selectedIndices.add(index);
                }
                repaint();
            }
        }
        
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
    }
}
