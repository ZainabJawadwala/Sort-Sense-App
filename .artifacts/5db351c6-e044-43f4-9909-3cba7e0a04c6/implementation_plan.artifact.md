# Implementation Plan - Professional Backtracking & Clearer Games

The goal is to elevate the Backtracking module by adding more polished visualizations, completing the algorithm set, and making the "games" (like Sudoku and N-Queens) clearer and more interactive.

## User Review Required

> [!IMPORTANT]
> - I will implement a **"Interactive Mode"** for Sudoku, N-Queens, and Rat in a Maze, allowing students to try solving the puzzle themselves before clicking "Play" to see the backtracking solution.
> - I will upgrade the `BacktrackingVisualizer` to render high-quality grid systems (e.g., Sudoku's 3x3 sub-grids) and use distinct icons for Queens and Maze walls.
> - The remaining algorithms (**Rat in a Maze**, **Permutations**, **Maze Solver**) will be fully implemented with dedicated visualizers.

## Proposed Changes

### [Visualizer Component]

#### [MODIFY] [BacktrackingVisualizer.java](file:///C:/Android%20app/SortSense_app/app/src/main/java/com/sortsense/app/visualizer/BacktrackingVisualizer.java)
- Add rendering logic for **Sudoku** (bold lines for 3x3 boxes, drawing digits 1-9).
- Add rendering logic for **Maze** (Walls, Path, Target).
- Add specialized rendering for **N-Queens** (Distinct Queen icons or markers).
- Implement `onTouchEvent` to allow users to interact with cells (place queens, input Sudoku numbers, toggle maze walls).

### [Algorithms Component]

#### [NEW] [RatInAMazeAlgorithm.java](file:///C:/Android%20app/SortSense_app/app/src/main/java/com/sortsense/app/algorithms/backtracking/RatInAMazeAlgorithm.java)
- Implementation of classic Rat in a Maze backtracking with step-by-step updates.

#### [NEW] [PermutationAlgorithm.java](file:///C:/Android%20app/SortSense_app/app/src/main/java/com/sortsense/app/algorithms/backtracking/PermutationAlgorithm.java)
- Visualizing how a string's permutations are built using a state-space tree or a sequence of swaps.

#### [NEW] [MazeSolverAlgorithm.java](file:///C:/Android%20app/SortSense_app/app/src/main/java/com/sortsense/app/algorithms/backtracking/MazeSolverAlgorithm.java)
- Variation of the maze algorithm that finds all possible paths or the shortest path.

### [UI Components]

#### [MODIFY] [fragment_backtracking_visualizer.xml](file:///C:/Android%20app/SortSense_app/app/src/main/res/layout/fragment_backtracking_visualizer.xml)
- Add a "Try it Yourself" ToggleButton.
- Add status text (e.g., "Safe", "Backtracking...", "Conflict Found!").

#### [MODIFY] [BacktrackingVisualizerFragment.java](file:///C:/Android%20app/SortSense_app/app/src/main/java/com/sortsense/app/fragments/BacktrackingVisualizerFragment.java)
- Logic to switch between "Auto-Solve" and "Interactive" modes.
- Initialize the new algorithms based on the selection.

### [Educational Content]

#### [MODIFY] [BacktrackingTheoryFragment.java](file:///C:/Android%20app/SortSense_app/app/src/main/java/com/sortsense/app/fragments/BacktrackingTheoryFragment.java)
- Fully populate the theory, steps, and complexity for all 5 backtracking algorithms.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:assembleDebug` to ensure all new algorithm classes and UI layouts compile.

### Manual Verification
1. Open **Backtracking** -> **Sudoku**.
2. Verify the grid has bold 3x3 dividers and clear numbers.
3. Toggle **"Try it Yourself"** and click a cell to input a number.
4. Click **Play** and watch the backtracking solver fill the remaining cells.
5. Repeat for **Rat in a Maze** and **N-Queens**.
