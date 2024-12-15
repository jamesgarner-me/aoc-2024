package aoc.day12;

import aoc.Day;
import aoc.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 implements Day {

    private char[][] farmArray;
    private boolean[][] farmArrayVisited;

    private int rowSize;
    private int colSize;

    private int perimeterArea = 0;
    private int perimeterValue = 0;

    private int[] dx = {-1, 0, 1, 0};
    private int[] dy = {0, 1, 0, -1};

    // Used in Part 1 & Part 2
    private void initializeFarmArrays(List<String> lines) {

        this.rowSize = lines.size();
        this.colSize = lines.getFirst().length();

        this.farmArray = new char[rowSize][colSize];
        this.farmArrayVisited = new boolean[rowSize][colSize];

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                this.farmArray[row][col] = lines.get(row).charAt(col);
                this.farmArrayVisited[row][col] = false;
            }
        }
    }

    // part 1 - count sides
    private int getPerimeterValueForPlot(int row, int col) {

        int perimeter = 4; // initial size; all four sizes of a plot

        char plotType = this.farmArray[row][col];

        // System.out.print("Plot: " + plotType + "[" + row + "][" + col + "] | ");

        // look left
        if ((col - 1) >= 0 && this.farmArray[row][col - 1] == plotType) {
            perimeter -= 1;
        }
        // look right
        if ((col + 1) < this.colSize && this.farmArray[row][col + 1] == plotType) {
            perimeter -= 1;
        }
        // look up
        if ((row - 1) >= 0 && this.farmArray[row - 1][col] == plotType) {
            perimeter -= 1;
        }
        // look down
        if ((row + 1) < this.rowSize && this.farmArray[row + 1][col] == plotType) {
            perimeter -= 1;
        }
        // System.out.println("perimeter val: " + perimeter);
        return perimeter;
    }

    // part 2 - count corners
    private int getCornerValueForPlot(int row, int col) {

        int corners = 0;

        char plotType = this.farmArray[row][col];

        // System.out.print("Plot: " + plotType + "[" + row + "][" + col + "] | ");

        // Top Left Convex
        // Left plot is edge or different plot type
        if (col == 0 || this.farmArray[row][col - 1] != plotType) {
            // Top Left Convex
            // Top plot is edge or different plot type
            if (row == 0 || this.farmArray[row - 1][col] != plotType) {
                corners += 1;
            }
            // Bottom Left Convex
            // Bottom plot is edge or different plot type
            if (row + 1 >= this.rowSize || this.farmArray[row + 1][col] != plotType) {
                corners += 1;
            }
        }

        // Top Right Convex
        // Right plot is edge or different plot type
        if (col + 1 >= this.colSize || this.farmArray[row][col + 1] != plotType) {
            // Top plot is edge or different plot type
            if (row == 0 || this.farmArray[row - 1][col] != plotType) {
                corners += 1;
            }
            // Bottom Right Convex
            // Bottom plot is edge or different plot type
            if (row + 1 >= this.rowSize || this.farmArray[row + 1][col] != plotType) {
                corners += 1;
            }
        }


        // Bottom Left Concave - (from plot diagonal to corner)
        // Top is same plot
        if (row - 1 >= 0 && this.farmArray[row - 1][col] == plotType) {
            // Right is same plot, row-1 col+1 is different plot
            if (col + 1 < this.colSize && this.farmArray[row][col + 1] == plotType &&
                    this.farmArray[row - 1][col + 1] != plotType) {
                corners += 1;
            }

            // Bottom Right Concave - (from plot diagonal to corner)
            // Left is same plot, row-1 col-1 is different plot
            if (col - 1 >= 0 && this.farmArray[row][col - 1] == plotType &&
                    this.farmArray[row - 1][col - 1] != plotType) {
                corners += 1;
            }
        }

        // Top Right Concave - (from plot diagonal to corner)
        // Bottom is same plot
        if (row + 1 < this.rowSize && this.farmArray[row + 1][col] == plotType) {
            // Left is same plot, row+1 col-1 is different plot
            if (col - 1 >= 0 && this.farmArray[row][col - 1] == plotType &&
                    this.farmArray[row + 1][col - 1] != plotType) {
                corners += 1;
            }
        }

        // Top Left Concave - (from plot diagonal to corner)
        // Bottom is same plot
        if (row + 1 < this.rowSize && this.farmArray[row + 1][col] == plotType) {
            // Right is same plot, row+1 col+1 is different plot
            if (col + 1 < this.colSize && this.farmArray[row][col + 1] == plotType &&
                    this.farmArray[row + 1][col + 1] != plotType) {
                corners += 1;
            }
        }

        // System.out.println("corners val: " + corners);
        return corners;
    }

    // recursive flood fill - visit all plots in a region, meanwhile counting perimeter and area
    private void fillRegion(int row, int col, char plotType, boolean isPartOne) {
        if (row < 0 || row >= this.rowSize || // outside farm boundary
                col < 0 || col >= this.colSize || // outside farm boundary
                this.farmArray[row][col] != plotType || // inside a different region
                (this.farmArray[row][col] == plotType && this.farmArrayVisited[row][col])) // same region but already visited
        {
            return;
        }

        this.perimeterArea += 1;
        if(isPartOne) {
            this.perimeterValue += getPerimeterValueForPlot(row, col);
        } else {
            this.perimeterValue += getCornerValueForPlot(row, col);
        }

        this.farmArrayVisited[row][col] = true;

        for (int i = 0; i < 4; i++) {
            int newRow = row + dx[i];
            int newCol = col + dy[i];
            fillRegion(newRow, newCol, plotType, isPartOne);
        }
    }

    private int runPart1or2Helper(String input, boolean isPartOne) {

        List<String> lines = Utils.splitLines(input);

        initializeFarmArrays(lines);

        HashMap<Character, Integer> plotMap = new HashMap<Character, Integer>();
        HashMap<Character, Integer> plotTypeCount = new HashMap<Character, Integer>();

        int totalPrice = 0;
        // count perimeter fences & count plot types
        for (int row = 0; row < this.rowSize; row++) {

            for (int col = 0; col < this.colSize; col++) {

                char currentPlotType = this.farmArray[row][col];

                if (!this.farmArrayVisited[row][col]) {

                    fillRegion(row, col, currentPlotType, isPartOne);

                    // System.out.println(currentPlotType + " | perimeterArea: " + this.perimeterArea + " perimeterValue: " + this.perimeterValue);

                    totalPrice += (this.perimeterArea * this.perimeterValue);

                    // reset for next region
                    this.perimeterArea = 0;
                    this.perimeterValue = 0;
                }
            }
        }
        return totalPrice;
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