# Milestone 4 – Data Structure Explanation (concise)

This summarizes exactly what changed from Milestone 3 and why (2 marks).

## What changed (UML/data)
- New classes:
  - `GameState` (snapshot of board, players, turn, passes, timestamp) – Serializable.
  - `UndoRedoManager` (two stacks of `GameState`, bounded history).
  - `GameSerializer` (save/load with MAGIC + VERSION checks).
  - `BoardConfig` (premium layouts for Standard/Diamond/Cross).
- Modified classes:
  - `ScrabbleModel`, `Board`, `Player` now Serializable.
  - `ScrabbleModel` gained `undo()`, `redo()`, `restoreState(...)`, `canUndo()`, `canRedo()`, and private `saveState()`.
  - View wiring: `ControlPanel` calls undo/redo; `ScrabbleView` uses `GameSerializer` and re‑binds panels to the loaded model.
- UML deltas:
  - New associations: `ScrabbleModel→UndoRedoManager`, `UndoRedoManager↔GameState`, `Board→BoardConfig`, `GameSerializer→ScrabbleModel`.

## Why these changes
- Undo/Redo via snapshots is simple and reliable:
  - Push before a change, pop on undo; bounded stacks avoid memory growth.
  - 15×15 board + small player racks make snapshots lightweight.
- Serializable model enables straightforward persistence:
  - `GameSerializer` isolates I/O; MAGIC/VERSION guard against bad files.
- Configurable `BoardConfig` cleanly supports multiple premium layouts.

## Impact
- Performance: Undo/Redo applies a 15×15 copy; fast for interactive use.
- Correctness: Restores board tiles, scores, racks, and current player.
- Compatibility: View API unchanged; functionality extended without breaking Milestone 3 usage.


