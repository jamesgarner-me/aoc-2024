package aoc.day10;

import aoc.Day;
import aoc.Utils;

import java.util.List;

public class Day10 implements Day {

    private int[][] trailMap;
    private boolean[][] visitedPeaks;

    private int rowSize;
    private int colSize;

    private int trailheadScore = 0;

    private final int TRAIL_END = 9;

    private void initializeTrailMap(List<String> lines) {

        this.rowSize = lines.size();
        this.colSize = lines.getFirst().length();

        this.trailMap = new int[rowSize][colSize];

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                this.trailMap[row][col] = Integer.parseInt(Character.toString(lines.get(row).charAt(col)));
            }
        }
    }

    // recursively search surrounding topographies for next trail step
    private void trailSearch(int row, int col, int currentValue, boolean isPartOne) {
        // base case - boundary check
        if (row < 0 || row >= this.rowSize || col < 0 || col >= this.colSize) {
            return;
        }

        // base case - current position must be the value we're looking for
        if (this.trailMap[row][col] != currentValue) {
            return;
        }

        // base case - peak found
        if (currentValue == TRAIL_END && !visitedPeaks[row][col]) {
            this.trailheadScore++;
            if(isPartOne) {
                visitedPeaks[row][col] = true;
            }
            return;
        }

        int nextValue = currentValue + 1;

        // Check all four directions for the next height
        if (row - 1 >= 0) {
            trailSearch(row - 1, col, nextValue, isPartOne);
        }
        if (row + 1 < this.rowSize) {
            trailSearch(row + 1, col, nextValue, isPartOne);
        }
        if (col - 1 >= 0) {
            trailSearch(row, col - 1, nextValue, isPartOne);
        }
        if (col + 1 < this.colSize) {
            trailSearch(row, col + 1, nextValue, isPartOne);
        }
    }

    private int runPart1or2Helper(String input, boolean isPartOne) {
        List<String> lines = Utils.splitLines(input);

        initializeTrailMap(lines);

        this.trailheadScore = 0;

        for (int row = 0; row < this.rowSize; row++) {

            for (int col = 0; col < this.colSize; col++) {

                if (this.trailMap[row][col] == 0) {

                    this.visitedPeaks = new boolean[this.rowSize][this.colSize];  // Reset visitedPeaks for next trailhead

                    trailSearch(row, col, 0, isPartOne);

                }
            }
        }

        return this.trailheadScore;
    }

    @Override
    public String part1(String input) {

        return Integer.toString(runPart1or2Helper(input, true));

    }

    @Override
    public String part2(String input) {

        return Integer.toString(runPart1or2Helper(input, false));

    }
}
