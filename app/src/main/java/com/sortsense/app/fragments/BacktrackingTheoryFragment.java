package com.sortsense.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.sortsense.app.R;

public class BacktrackingTheoryFragment extends Fragment {

    private String algoName;
    private LinearLayout layoutSteps;
    private TextView txtExplanation, txtComplexity;

    public static BacktrackingTheoryFragment newInstance(String algoName) {
        BacktrackingTheoryFragment fragment = new BacktrackingTheoryFragment();
        Bundle args = new Bundle();
        args.putString("ALGO_NAME", algoName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            algoName = getArguments().getString("ALGO_NAME");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_backtracking_theory, container, false);

        txtExplanation = view.findViewById(R.id.txtExplanation);
        txtComplexity = view.findViewById(R.id.txtComplexity);
        layoutSteps = view.findViewById(R.id.layoutSteps);

        updateUI();

        return view;
    }

    private void updateUI() {
        if (!isAdded()) return;
        layoutSteps.removeAllViews();
        switch (algoName) {
            case "N-Queens":
                txtExplanation.setText("The N-Queens puzzle is the classic problem of placing N chess queens on an N×N chessboard so that no two queens threaten each other. This means no two queens can share the same row, column, or diagonal. The backtracking algorithm explores possible configurations and abandons (backtracks) as soon as it determines a path cannot lead to a valid solution.");
                addStep("1. Initialization", "Start with an empty N×N chessboard and begin in the first column (column 0).", R.drawable.ic_diag_nqueen_v2);
                addStep("2. Recursive Placement", "For the current column, try placing a queen in each row (0 to N-1) one by one.", R.drawable.ic_diag_nqueen_v2);
                addStep("3. Safety Check", "Check if placing the queen at [row, col] is safe. It is safe if no other queen exists in the same row, upper-left diagonal, or lower-left diagonal.", R.drawable.ic_diag_nqueen_v2);
                addStep("4. Forward Step", "If the position is safe, mark it and recursively move to the next column (col + 1).", R.drawable.ic_diag_nqueen_v2);
                addStep("5. Backtrack Step", "If no safe row is found in the current column, or if a recursive call returns false, remove the queen from the current position and try the next row.", R.drawable.ic_diag_nqueen_v2);
                addStep("6. Base Case", "If the column index reaches N, it means all queens are placed safely. Return true to signal success.", R.drawable.ic_diag_nqueen_v2);
                txtComplexity.setText("Time Complexity: O(N!) – The algorithm tries many possible queen placements.\n\nSpace Complexity: O(N) – Stores queen positions and recursion stack.");
                break;

            case "Rat in a Maze":
                txtExplanation.setText("The Rat in a Maze problem involves finding a path from a starting point (0,0) to a destination (N-1, N-1) in a grid. The maze consists of cells that are either open (1) or blocked by walls (0). The rat can typically move in two directions: Right or Down.");
                addStep("1. Path Tracking", "Initialize a solution matrix of size N×N with all 0s to track the final path taken by the rat.", R.drawable.ic_diag_rat_maze_v2);
                addStep("2. Validation", "Check if the current cell (row, col) is valid: it must be within the maze boundaries and not be a wall (must be 1).", R.drawable.ic_diag_rat_maze_v2);
                addStep("3. Mark Path", "If valid, mark the current cell as 1 in the solution matrix to indicate it is part of the path.", R.drawable.ic_diag_rat_maze_v2);
                addStep("4. Goal Check", "If the current cell is the destination (N-1, N-1), the problem is solved. Return true.", R.drawable.ic_diag_rat_maze_v2);
                addStep("5. Directional Search", "Try moving Down (row+1). If that fails, try moving Right (col+1).", R.drawable.ic_diag_rat_maze_v2);
                addStep("6. Backtrack", "If neither direction leads to the goal, unmark the current cell (reset to 0) and return to the previous cell.", R.drawable.ic_diag_rat_maze_v2);
                txtComplexity.setText("Time Complexity: O(2^(N×N)) – Explores many possible paths in the maze.\n\nSpace Complexity: O(N×N) – Stores visited cells and recursion stack.");
                break;

            case "Sudoku Solver":
                txtExplanation.setText("Sudoku is a logic-based puzzle where the goal is to fill a 9×9 grid so that each row, column, and each of the nine 3×3 subgrids contains all digits from 1 to 9. Backtracking is used to fill cells and undo choices that lead to violations.");
                addStep("1. Locate Empty Cell", "Scan the 9×9 grid to find the next available empty cell (represented by 0).", R.drawable.ic_diag_sudoku_v2);
                addStep("2. Candidate Trial", "Try placing digits from 1 to 9 in the empty cell sequentially.", R.drawable.ic_diag_sudoku_v2);
                addStep("3. Constraint Check", "Ensure the chosen digit is not already present in the current row, column, or 3×3 subgrid.", R.drawable.ic_diag_sudoku_v2);
                addStep("4. Recurse", "If the digit is valid, move to the next empty cell and repeat the process.", R.drawable.ic_diag_sudoku_v2);
                addStep("5. Backtrack", "If no digit leads to a full solution, reset the current cell to 0 and return to the previous cell to try a different digit.", R.drawable.ic_diag_sudoku_v2);
                txtComplexity.setText("Time Complexity: O(9^(N*N)) – In the worst case, every empty cell has up to 9 choices to explore.\n\nSpace Complexity: O(N*N) – Stores the grid data and the recursion stack.");
                break;

            case "Permutations":
                txtExplanation.setText("A Permutation Generator finds all possible unique arrangements of a set of elements (like characters in a string). For a string of length N, there are N! (N factorial) possible permutations.");
                addStep("1. Fixing Position", "Choose an element from the available set and fix it at the current starting position using a swap.", R.drawable.ic_diag_permutation);
                addStep("2. Recursive Call", "Recursively generate permutations for the remaining elements of the set by moving the 'start' pointer forward.", R.drawable.ic_diag_permutation);
                addStep("3. Base Case Reached", "When the starting position reaches the end of the array, a complete permutation has been formed. Store or print it.", R.drawable.ic_diag_permutation);
                addStep("4. Backtrack Swap", "Swap the elements back to their original positions to restore the state, allowing the next element to be fixed at the current position.", R.drawable.ic_diag_permutation);
                txtComplexity.setText("Time Complexity: O(N × N!) – Generates all possible permutations.\n\nSpace Complexity: O(N) – Uses recursion stack and temporary storage.");
                break;

            case "Maze Solver":
                txtExplanation.setText("The Maze Solver is a general backtracking algorithm used to find a route through a complex labyrinth. Unlike basic solvers, it usually explores all 4 directions (Up, Down, Left, Right) to handle intricate paths and dead ends.");
                addStep("1. Setup", "Begin at the entry point. Maintain a 'visited' matrix to keep track of cells already explored to avoid infinite cycles.", R.drawable.ic_diag_rat_maze_v2);
                addStep("2. Exploration", "From the current cell, check all 4 neighbors: Up, Down, Left, and Right.", R.drawable.ic_diag_rat_maze_v2);
                addStep("3. Safety & Visited", "A move is valid if the cell is within bounds, not a wall, and has not been visited yet.", R.drawable.ic_diag_rat_maze_v2);
                addStep("4. Path Discovery", "If a neighbor is valid, mark it as visited and move into it recursively.", R.drawable.ic_diag_rat_maze_v2);
                addStep("5. Goal Reached", "If the current cell is the exit, return true to stop the search.", R.drawable.ic_diag_rat_maze_v2);
                addStep("6. Backtrack Path", "If all directions from a cell fail, unmark the cell as visited (if searching for all paths) or simply return false to backtrack to the previous intersection.", R.drawable.ic_diag_rat_maze_v2);
                txtComplexity.setText("Time Complexity: O(2^(N×N)) – Searches through multiple possible routes.\n\nSpace Complexity: O(N×N) – Keeps track of visited cells and recursive calls.");
                break;

            default:
                txtExplanation.setText("Comprehensive theory and step-by-step guides for " + algoName + " are coming soon!");
                txtComplexity.setText("N/A");
        }
    }

    private void addStep(String title, String description, int imageResId) {
        if (!isAdded()) return;
        View stepView = getLayoutInflater().inflate(R.layout.item_theory_step, layoutSteps, false);
        TextView txtTitle = stepView.findViewById(R.id.txtStepTitle);
        TextView txtDesc = stepView.findViewById(R.id.txtStepDescription);
        ImageView imgStep = stepView.findViewById(R.id.imgStep);

        txtTitle.setText(title);
        txtDesc.setText(description);
        if (imageResId != 0) {
            imgStep.setImageResource(imageResId);
        } else {
            imgStep.setVisibility(View.GONE);
        }
        layoutSteps.addView(stepView);
    }
}
