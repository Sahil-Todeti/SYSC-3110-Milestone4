package scrabble.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import scrabble.model.Player;

/**
 * Panel displaying all players' scores.
 * 
 * @author Ashfaqul Alam
 * @version Milestone 4
 */
public class ScorePanel extends JPanel {
    
    private JTextArea scoreArea;
    private List<Player> players;
    
    public ScorePanel(List<Player> players) {
        this.players = players;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 600));
        
        scoreArea = new JTextArea();
        scoreArea.setEditable(false);
        scoreArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(scoreArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Scores"));
        
        add(scrollPane, BorderLayout.CENTER);
        
        updateScores(players);
    }
    
    public void updateScores(List<Player> players) {
        this.players = players;
        StringBuilder sb = new StringBuilder();
        sb.append("=== SCORES ===\n\n");
        
        int rank = 1;
        for (Player player : players) {
            String indicator = player.isAI() ? " (AI)" : "";
            sb.append(rank).append(". ").append(player.getName()).append(indicator).append(":\n");
            sb.append("   ").append(String.format("%5d", player.getScore())).append(" pts\n\n");
            rank++;
        }
        
        scoreArea.setText(sb.toString());
    }
    
    public void refreshScores() {
        if (players != null) {
            updateScores(players);
        }
    }
}
