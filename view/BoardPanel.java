package scrabble.view;

import javax.swing.*;
import java.awt.*;
import scrabble.model.Board;
import scrabble.model.PremiumSquare;

/**
 * Panel that renders the Scrabble game board.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class BoardPanel extends JPanel {
    
    private Board board;
    private static final int CELL_SIZE = 45;
    private static final int GRID_SIZE = 15;
    
    public BoardPanel(Board board) {
        this.board = board;
        setPreferredSize(new Dimension(CELL_SIZE * GRID_SIZE + 1, CELL_SIZE * GRID_SIZE + 1));
        setBackground(Color.WHITE);
    }
    
    public void setBoard(Board newBoard) {
        if (newBoard != null) {
            this.board = newBoard;
            repaint();
            revalidate();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (board == null) return;
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                
                drawPremiumSquareBackground(g2d, x, y, row, col);
                
                g2d.setColor(Color.DARK_GRAY);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                
                Character tile = board.get(row, col);
                if (tile != null && tile != '_' && tile != ' ') {
                    drawTile(g2d, x, y, tile);
                }
            }
        }
    }
    
    private void drawPremiumSquareBackground(Graphics2D g2d, int x, int y, int row, int col) {
        PremiumSquare premium = board.getPremium(row, col);
        
        if (premium == PremiumSquare.TRIPLE_WORD) {
            g2d.setColor(new Color(200, 50, 50));
        } else if (premium == PremiumSquare.DOUBLE_WORD) {
            g2d.setColor(new Color(220, 100, 100));
        } else if (premium == PremiumSquare.TRIPLE_LETTER) {
            g2d.setColor(new Color(50, 100, 200));
        } else if (premium == PremiumSquare.DOUBLE_LETTER) {
            g2d.setColor(new Color(100, 150, 255));
        } else {
            g2d.setColor(new Color(245, 245, 220));
        }
        
        g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
    }
    
    private void drawTile(Graphics2D g2d, int x, int y, Character tile) {
        g2d.setColor(new Color(255, 250, 205));
        g2d.fillRoundRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4, 6, 6);
        
        g2d.setColor(new Color(139, 90, 43));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4, 6, 6);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g2d.getFontMetrics();
        
        String letter = String.valueOf(tile);
        int letterX = x + (CELL_SIZE - fm.stringWidth(letter)) / 2;
        int letterY = y + ((CELL_SIZE - fm.getHeight()) / 2) + fm.getAscent();
        
        g2d.drawString(letter, letterX, letterY);
    }
    
    public void refreshBoard() {
        if (board != null) {
            repaint();
            revalidate();
        }
    }
}
