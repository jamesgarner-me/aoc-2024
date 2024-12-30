package aoc.day18;

import aoc.Day;
import aoc.Utils;

import java.util.*;

public class Day18 implements Day {

    private char[][] memoryArray;
    private boolean[][] memoryArrayVisited;

    private int rowSize;
    private int colSize;

    private final int GRID_SIZE = 71; // input coords range 0-70
    private final int CORRUPT_KB_END_PTR = 1024;

    private final int[] dy = {-1, 1, 0, 0};
    private final int[] dx = {0, 0, 1, -1};

    private void printMemoryArray() {
        System.out.print("\n");
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                System.out.print(" " + this.memoryArray[row][col]);

            }
            System.out.print("\n");
        }
    }

    // Used in Part 1 & Part 2
    private void initializeMemoryArrays(List<String> lines, int kbEndPointer, int gridSize) {

        this.rowSize = gridSize;
        this.colSize = gridSize;

        this.memoryArray = new char[this.rowSize][this.colSize];
        this.memoryArrayVisited = new boolean[this.rowSize][this.colSize];

        for (int row = 0; row < this.rowSize; row++) {
            for (int col = 0; col < this.colSize; col++) {
                this.memoryArray[row][col] = '.';
                this.memoryArrayVisited[row][col] = false;
            }
        }
        // simulate corrupted memory
        for (int i = 0; i < kbEndPointer; i++) {
            String[] kbs = lines.get(i).split(",");
            int col = Integer.parseInt(kbs[0]);
            int row = Integer.parseInt(kbs[1]);
            this.memoryArray[row][col] = '#';
        }
    }

    // BFS - find a route, avoid corrupted memory positions
    private int findPath(int endRow, int endCol) {
        Queue<int[]> queue = new LinkedList<>();
        boolean reachedEnd = false;
        int stepCount = 0;

        int[] start = new int[]{0, 0};
        int[] end = new int[]{endRow, endCol};

        // Add start to queue
        queue.add(start);
        this.memoryArrayVisited[start[0]][start[1]] = true;

        int row, col;
        int memoryPositionsLeftInCurrentLayer = 1;
        int memoryPositionsInNextLayer = 0;

        while (!queue.isEmpty()) {

            int[] currCoord = queue.poll();
            row = currCoord[0];
            col = currCoord[1];

            // Check if we found the end.
            if (row == end[0] && col == end[1]) {
                reachedEnd = true;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int newRow = row + dy[i];
                int newCol = col + dx[i];

                if (newRow < 0 || newRow >= this.rowSize || // outside memory boundary
                        newCol < 0 || newCol >= this.colSize || // outside memory boundary
                        this.memoryArray[newRow][newCol] == '#' || // memory is corrupt
                        this.memoryArrayVisited[newRow][newCol]) // already visited
                {
                    continue;
                }

                // add to the queue
                queue.add(new int[]{newRow, newCol});
                this.memoryArrayVisited[newRow][newCol] = true;
                memoryPositionsInNextLayer += 1;
            }

            memoryPositionsLeftInCurrentLayer -= 1;

            if (memoryPositionsLeftInCurrentLayer == 0) {
                memoryPositionsLeftInCurrentLayer = memoryPositionsInNextLayer;
                memoryPositionsInNextLayer = 0;
                stepCount += 1;
            }

        }

        if (reachedEnd) {
            return stepCount;
        }

        return -1;
    }

    private String runPart1or2Helper(String input, boolean isPartOne) {

        List<String> lines = Utils.splitLines(input);

        // Example grid size - comment for full data set
        int gridSize = 7;
        int endRow = gridSize - 1;
        int endCol = gridSize - 1;

        // Uncomment for full data set
        // int endRow = GRID_SIZE - 1;
        // int endCol = GRID_SIZE - 1;

        int kbMaxSize = lines.size();

        String result = "";

        if (isPartOne) {
            // Example Corrupt KB pointer value - comment for full data set
            int kbEndPointer = 12;
            // Uncomment for full data set
            // int kbEndPointer = CORRUPT_KB_END_PTR;

            initializeMemoryArrays(lines, kbEndPointer, gridSize);
            result = Integer.toString(findPath(endRow, endCol));

        } else {
            // Example Corrupt KB pointer value - comment for full data set
            int kbStartPointer = 12;
            // Uncomment for full data set
            // int kbStartPointer = CORRUPT_KB_END_PTR;

            for (int i = kbStartPointer; i < kbMaxSize; i++) {

                initializeMemoryArrays(lines, i, gridSize);

                // check if no path found
                if (findPath(endRow, endCol) == -1) {
                    String[] kbs = lines.get(i - 1).split(",");
                    result = String.format("%s,%s", kbs[0], kbs[1]);
                    break;
                }

            }

        }

        return result;
    }


    @Override
    public String part1(String input) {

        return runPart1or2Helper(input, true);

    }


    @Override
    public String part2(String input) {

        return runPart1or2Helper(input, false);

    }
}