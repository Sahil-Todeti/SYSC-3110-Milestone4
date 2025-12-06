# Scrabble – User Manual (Milestone 4)

This guide explains how to run the game, play the basics, and use board selection, Undo/Redo, and Save/Load.

## 1) How to Run

### Option A: Run from an IDE (recommended)
- Open the project in IntelliJ IDEA (or another Java IDE).
- Ensure project SDK is Java 8+.
- Run the `scrabble.Main` class.

### Option B: Run from Terminal (source)
- From the project root:
  - Compile:
    - Windows (PowerShell):
      ```
      javac -d out src *.java
      ```
      If your IDE already compiled, you can skip this.
  - Run:
    ```
    java scrabble.Main
    ```

### Option C: Run a Jar (if provided)
- Double-click the jar or run:
  ```
  java -jar Scrabble.jar
  ```

## 2) Starting a Game
1. On launch, select a board layout:
   - Standard, Diamond, or Cross.
2. Enter the number of players (2–4).
3. Enter each player’s name.
4. The main window opens with:
   - Board (center),
   - Control Panel (right),
   - Tile Rack (bottom),
   - Current player label (top-left).

## 3) Playing the Game
- Turns rotate among players.
- Each player’s rack contains up to 7 tiles. Words are placed horizontally or vertically.
- Scoring uses premium squares (double/triple letter/word).

### Place a Word
1. Click “Place Word” in the Control Panel.
2. Enter:
   - Row (0–14),
   - Column (0–14),
   - Direction (Horizontal/Vertical),
   - The Word (letters only).
3. If valid, tiles appear on the board, score is added, and the turn passes to the next player.

### Exchange Tiles
- Click “Exchange Tiles” to skip turn and refresh tiles from the bag (when available).

### Pass Turn
- Click “Pass Turn” to skip without exchanging.

## 4) Undo / Redo
- The Control Panel has “<< Undo” and “Redo >>” buttons.
- Undo reverses the last successful action that saved game state (e.g., a placed word).
- Redo reapplies the last undone action.
- Buttons enable/disable automatically depending on availability.

## 5) Saving and Loading Games
### Save
1. File → “Save Game”.
2. Choose a filename (the game saves as `.dat`).
3. A confirmation dialog appears if successful.

### Load
1. File → “Load Game”.
2. Select a `.dat` save file.
3. The UI updates to the loaded game state (board, racks, scores, current player).

Notes:
- Save files include a magic header and version for basic validation.
- If a file is invalid or incompatible, you’ll see an error dialog.

## 6) Tips
- Words must be valid according to the dictionary; invalid words are rejected.
- If you can’t place a word, try a different position/direction or exchange/pass.
- Use Undo/Redo to explore placements safely.


