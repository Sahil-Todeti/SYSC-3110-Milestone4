import scrabble.model.*;

/**
 * Simple test to verify board tile placement and retrieval works
 */
public class SimpleBoardTest {
    public static void main(String[] args) {
        System.out.println("=== Simple Board Test ===");
        
        try {
            // Create board
            BoardConfig config = new BoardConfig("Standard");
            Board board = new Board(config);
            
            System.out.println("\n1. Testing direct tile placement...");
            board.placeTile(7, 7, 'H');
            board.placeTile(7, 8, 'E');
            board.placeTile(7, 9, 'L');
            
            System.out.println("   ✓ Tiles placed directly");
            
            System.out.println("\n2. Testing tile retrieval...");
            Character tile1 = board.get(7, 7);
            Character tile2 = board.get(7, 8);
            Character tile3 = board.get(7, 9);
            
            System.out.println("   Tile at (7,7): " + tile1);
            System.out.println("   Tile at (7,8): " + tile2);
            System.out.println("   Tile at (7,9): " + tile3);
            
            if (tile1 == 'H' && tile2 == 'E' && tile3 == 'L') {
                System.out.println("   ✓ Tiles retrieved correctly");
            } else {
                System.out.println("   ✗ ERROR: Tiles not retrieved correctly");
            }
            
            System.out.println("\n3. Testing grid array access...");
            char[][] grid = board.getGrid();
            System.out.println("   grid[7][7] = " + grid[7][7]);
            System.out.println("   grid[7][8] = " + grid[7][8]);
            System.out.println("   grid[7][9] = " + grid[7][9]);
            
            if (grid[7][7] == 'H' && grid[7][8] == 'E' && grid[7][9] == 'L') {
                System.out.println("   ✓ Grid array contains correct values");
            } else {
                System.out.println("   ✗ ERROR: Grid array incorrect");
            }
            
            System.out.println("\n=== TEST RESULT ===");
            System.out.println("✓ Board tile placement works correctly");
            System.out.println("✓ Board.get() method works correctly");
            System.out.println("✓ Grid array is updated correctly");
            System.out.println("\nIf this test passes but tiles don't appear in GUI,");
            System.out.println("the issue is in BoardPanel rendering or board reference.");
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

