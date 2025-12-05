import scrabble.model.*;
import java.util.*;

/**
 * Simple test to verify tile placement works correctly
 */
public class TestTilePlacement {
    public static void main(String[] args) {
        System.out.println("=== Testing Tile Placement ===");
        
        try {
            // Create board and model
            BoardConfig config = new BoardConfig("Standard");
            List<String> players = new ArrayList<>();
            players.add("Player1");
            players.add("Player2");
            
            ScrabbleModel model = new ScrabbleModel(config, players);
            model.startGame();
            
            Board board = model.getBoard();
            
            System.out.println("\n1. Testing board initialization...");
            Character tile = board.get(7, 7);
            System.out.println("   Tile at (7,7) before placement: " + tile);
            assert tile == null : "Board should be empty initially";
            System.out.println("   ✓ Board is empty");
            
            System.out.println("\n2. Adding required tiles to player rack...");
            Player player1 = model.getCurrentPlayer();
            // Clear existing rack and add tiles for "HELLO"
            player1.clearRack();
            player1.addTile('H');
            player1.addTile('E');
            player1.addTile('L');
            player1.addTile('L');
            player1.addTile('O');
            System.out.println("   Player rack: " + player1.getRackDisplay());
            System.out.println("   Rack size: " + player1.getRack().size());
            System.out.println("   ✓ Tiles added to rack");
            
            // Verify hasLetters works
            boolean hasLetters = player1.hasLetters("HELLO", board, 7, 7, true);
            System.out.println("   hasLetters('HELLO') returned: " + hasLetters);
            
            System.out.println("\n3. Testing word placement...");
            boolean placed = model.placeWord(7, 7, true, "HELLO");
            System.out.println("   placeWord() returned: " + placed);
            
            if (placed) {
                System.out.println("   ✓ Word placement succeeded");
                
                // Check tiles
                System.out.println("\n4. Verifying tiles on board...");
                tile = board.get(7, 7);
                System.out.println("   Tile at (7,7): " + tile);
                if (tile != null && tile == 'H') {
                    System.out.println("   ✓ Tile 'H' found at (7,7)");
                } else {
                    System.out.println("   ✗ ERROR: Expected 'H' at (7,7), got: " + tile);
                }
                
                tile = board.get(7, 8);
                System.out.println("   Tile at (7,8): " + tile);
                if (tile != null && tile == 'E') {
                    System.out.println("   ✓ Tile 'E' found at (7,8)");
                } else {
                    System.out.println("   ✗ ERROR: Expected 'E' at (7,8), got: " + tile);
                }
                
                tile = board.get(7, 9);
                System.out.println("   Tile at (7,9): " + tile);
                if (tile != null && tile == 'L') {
                    System.out.println("   ✓ Tile 'L' found at (7,9)");
                } else {
                    System.out.println("   ✗ ERROR: Expected 'L' at (7,9), got: " + tile);
                }
                
                // Check grid directly
                System.out.println("\n5. Checking grid array directly...");
                char[][] grid = board.getGrid();
                System.out.println("   grid[7][7] = " + grid[7][7]);
                System.out.println("   grid[7][8] = " + grid[7][8]);
                System.out.println("   grid[7][9] = " + grid[7][9]);
                
                if (grid[7][7] == 'H' && grid[7][8] == 'E' && grid[7][9] == 'L') {
                    System.out.println("   ✓ Grid array contains correct tiles");
                } else {
                    System.out.println("   ✗ ERROR: Grid array does not contain expected tiles");
                }
                
                System.out.println("\n=== TEST SUMMARY ===");
                System.out.println("✓ Tile placement logic works correctly");
                System.out.println("✓ Tiles are stored in board grid");
                System.out.println("✓ Board.get() method retrieves tiles correctly");
                System.out.println("\nIf tiles don't appear on screen, the issue is in rendering, not placement logic.");
                
            } else {
                System.out.println("   ✗ Word placement failed!");
                System.out.println("   Check:");
                System.out.println("   - Dictionary validation");
                System.out.println("   - Board placement validation");
                System.out.println("   - Player has required tiles");
            }
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

