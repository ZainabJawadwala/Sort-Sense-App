# Unified Algorithm Visualizer Refactor

This plan transforms the app into a streamlined algorithm visualizer focused on clear, colorful, and interactive graphical interfaces for both searching and sorting algorithms.

## User Review Required

> [!IMPORTANT]
> **Functional Changes:**
> 1. All social features (Doubt Solver, Posts, Comments) will be removed.
> 2. The main interface will now be a list of all available algorithms.
> 3. A single, unified visualizer interface will be used for both Searching and Sorting.
> 4. Algorithms will support **Start/Stop** and **Dynamic Speed/Bar Size** adjustment.

## Proposed Changes

### 1. Graphical Visualizer Update

#### [MODIFY] [SortingVisualizer.java](file:///C:/Android app/SortSense_app/app/src/main/java/com/sortsense/app/visualizer/SortingVisualizer.java)
- Update color scheme:
  - **Blue:** Default bar color.
  - **Red:** Comparing, Index, Pivot element.
  - **Green:** Swapping, Found.
- Add `barWidth` property to allow dynamic adjustment of bar thickness.
- Optimize drawing to ensure bars start from the bottom.

### 2. Core Algorithm Logic

#### [MODIFY] [SortingAlgorithm.java](file:///C:/Android app/SortSense_app/app/src/main/java/com/sortsense/app/algorithms/SortingAlgorithm.java)
- Add a `running` flag to support the **Start/Stop** functionality.
- Update algorithms (Bubble, Selection, Quick, etc.) to check this flag during execution.
- Ensure Searching algorithms (Linear, Binary) utilize the `SortingAlgorithm` base correctly for color signaling.

### 3. Unified User Interface

#### [NEW] [activity_visualizer.xml](file:///C:/Android app/SortSense_app/app/src/main/res/layout/activity_visualizer.xml)
- A streamlined layout containing:
  - Large visualization area.
  - **Array Size** slider.
  - **Speed (Delay)** slider.
  - **Bar Size** slider.
  - **Start/Stop** and **Shuffle** buttons.

#### [NEW] [VisualizerActivity.java](file:///C:/Android app/SortSense_app/app/src/main/java/com/sortsense/app/activities/VisualizerActivity.java)
- A single activity that receives the algorithm name via Intent.
- Handles UI interactions for all 7 algorithms.

### 4. Simplified Main Interface

#### [MODIFY] [activity_main.xml](file:///C:/Android app/SortSense_app/app/src/main/res/layout/activity_main.xml)
- Remove "Doubt Solver", "About Us", and other non-essential UI elements.
- Display a clean list/grid of all algorithms:
  - **Sorting:** Bubble, Selection, Insertion, Quick, Merge.
  - **Searching:** Linear, Binary.

#### [MODIFY] [MainActivity.java](file:///C:/Android app/SortSense_app/app/src/main/java/com/sortsense/app/activities/MainActivity.java)
- Update to handle navigation to the new `VisualizerActivity` for each algorithm.

### 5. Cleanup

#### [DELETE] Social Features
- Delete activities: `DoubtActivity`, `PostActivity`, `CommentActivity`, `MyPostsActivity`.
- Delete models: `Post`, `Comment`.
- Delete adapters: `PostAdapter`, `CommentAdapter`, `MyPostAdapter`.
- Delete old visualizer activities: `SortingActivity`, `SearchingActivity`.

## Verification Plan

### Automated Tests
1. Run `./gradlew assembleDebug` to ensure all cross-references are correctly updated.

### Manual Verification
1. **Navigation:** Verify that selecting "Bubble Sort" or "Binary Search" from the home screen opens the same interface.
2. **Visualizer:**
   - Confirm default color is Blue.
   - Confirm comparison/index bars turn Red.
   - Confirm swapping/found bars turn Green.
3. **Controls:**
   - Adjust "Bar Size" and verify the visual thickness changes.
   - Click "Start", then "Stop" mid-algorithm to verify control.
   - Adjust "Speed" and verify the delay changes in real-time.
