package aoc.day15;

import aoc.Utils;
import aoc.Day;

import java.util.ArrayList;
import java.util.List;

public class Day15 implements Day {

    private char[][] warehouse;
    private ArrayList<Character> moves;
    private int robotRow = 0;
    private int robotCol = 0;

    private void printWarehouse() {
        for (char[] chars : this.warehouse) {
            for (int j = 0; j < this.warehouse.length; j++) {
                System.out.print(chars[j]);
            }
            System.out.println();
        }
    }

    private void initialise(List<String> lines) {

        int size = lines.getFirst().length();
        this.warehouse = new char[size][size];

        // parse warehouse
        for (int row = 0; row < size; row++) {
            String line = lines.get(row);
            for (int col = 0; col < size; col++) {
                this.warehouse[row][col] = line.charAt(col);
                if (this.warehouse[row][col] == '@') {
                    this.robotRow = row;
                    this.robotCol = col;
                }
            }
        }

        // parse moves
        this.moves = new ArrayList<>();
        for (int row = size + 1; row < lines.size(); row++) {
            String line = lines.get(row);
            for (char c : line.toCharArray()) {
                this.moves.add(c);
            }
        }
    }

    private Boolean isWithinBoundary(int row, int col) {
        int size = this.warehouse.length;
        return row >= 0 && row < size && col >= 0 && col < size && !isWall(row, col);
    }

    private Boolean isEmpty(int row, int col) {
        return this.warehouse[row][col] == '.';
    }

    private Boolean isBox(int row, int col) {
        return this.warehouse[row][col] == 'O';
    }

    private Boolean isWall(int row, int col) {
        return this.warehouse[row][col] == '#';
    }

    private void moveRobot(int row, int col) {
        this.warehouse[this.robotRow][this.robotCol] = '.';
        this.robotRow = row;
        this.robotCol = col;
        this.warehouse[this.robotRow][this.robotCol] = '@';
    }

    private void moveRobotCheck(int rowOffset, int colOffset) {

        int newRow = this.robotRow + rowOffset;
        int newCol = this.robotCol + colOffset;

        if (isWithinBoundary(newRow, newCol)) {

            // no boxes in the way - move the robot and return
            if (isEmpty(newRow, newCol)) {

                moveRobot(newRow, newCol);

            } else if (isBox(newRow, newCol)) {

                // *** we're moving left/right ***
                if (colOffset == -1 || colOffset == 1) {

                    int currCol = newCol;

                    // Keep moving in offset direction until we skip all boxes or hit a wall
                    while (isBox(newRow, currCol) && !isWall(newRow, currCol)) {
                        currCol += colOffset;
                    }

                    // if empty space is found, move all boxes in between start coord and end coord
                    if (isEmpty(newRow, currCol)) {
                        int boxCount = Math.abs(newCol - currCol);
                        currCol = newCol;
                        for (int i = 0; i < boxCount; i++) {
                            currCol += colOffset;

                            // move box
                            if (!isWall(newRow, currCol)) {
                                this.warehouse[newRow][currCol] = 'O';
                            }
                        }
                        // finally, move the robot
                        moveRobot(newRow, newCol);
                    }
                    // otherwise no empty space is found, i.e. we hit a wall. Make no moves.
                    // else {}
                }

                // *** we're moving up/down ***
                if (rowOffset == -1 || rowOffset == 1) {

                    int currRow = newRow;

                    // Keep moving in offset direction until we skip all boxes or hit a wall
                    while (isBox(currRow, newCol) && !isWall(currRow, newCol)) {
                        currRow += rowOffset;
                    }

                    // if empty space is found, move all boxes in between start coord and end coord
                    if (isEmpty(currRow, newCol)) {
                        int boxCount = Math.abs(newRow - currRow);
                        currRow = newRow;
                        for (int i = 0; i < boxCount; i++) {

                            currRow += rowOffset;

                            // move box
                            if (!isWall(currRow, newCol)) {
                                this.warehouse[currRow][newCol] = 'O';
                            }
                        }
                        // finally, move the robot
                        moveRobot(newRow, newCol);
                    }
                    // otherwise no empty space is found, i.e. we hit a wall. Make no moves.
                    // else {}
                }
            }
        }
    }

    private String runPart1or2Helper(String input, boolean isPartOne) {
        int gpsCoordSum = 0;

        for (Character dir : moves) {

            switch (dir) {
                case '<' -> moveRobotCheck(0, -1);
                case '>' -> moveRobotCheck(0, 1);
                case '^' -> moveRobotCheck(-1, 0);
                case 'v' -> moveRobotCheck(1, 0);
            }

        }

        // Calculate sum of box coordinates
        int size =  this.warehouse.length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (isBox(row, col)) {
                    gpsCoordSum += 100 * row + col;
                }
            }
        }

        return Integer.toString(gpsCoordSum);
    }

    @Override
    public String part1(String input) {

        List<String> lines = Utils.splitLines(input);

        initialise(lines);

        return runPart1or2Helper(input, true);

    }


    // todo finish P2
    @Override
    public String part2(String input) {

        return runPart1or2Helper(input, false);

    }
}
