package aoc.day04;

import aoc.Day;
import aoc.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

public class Day04 implements Day {
    private String P1_REGEX_XMAS = "XMAS";
    private char[][] wordSearch;

    // Used in Part 1 & Part 2
    private char[][] initializeWordSearch(List<String> lines) {
        int rowSize = lines.size();
        int colSize = lines.getFirst().length();
        char[][] wordSearch = new char[rowSize][colSize];

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                wordSearch[row][col] = lines.get(row).charAt(col);
            }
        }
        return wordSearch;
    }

    // *** Start Part 1 helper methods
    private Integer countRegexOccurrences(String input, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        int count = 0;
        while (matcher.find()) {
            System.out.println("found XMAS: " + input);
            count++;
        }
        return count;
    }

    private Integer countRegexOccurrencesForwardAndReverse(String input, String regex) {
        int count = 0;
        if (input.length() >= regex.length()) {
            count += countRegexOccurrences(input, regex);
            String reversedInput = new StringBuilder(input).reverse().toString();
            count += countRegexOccurrences(reversedInput, regex);
        }
        return count;
    }
    // *** End Part 1 helper methods

    // *** Start Part 2 helper methods
    private boolean isValidXPattern(int row, int col) {
        return wordSearch[row][col] == 'A' &&
                (isValidDiagonal(row, col, -1, -1, 1, 1) &&
                        isValidDiagonal(row, col, -1, 1, 1, -1));
    }

    private boolean isValidDiagonal(int row, int col,
                                    int startRowOffset, int startColOffset,
                                    int endRowOffset, int endColOffset) {
        char start = wordSearch[row + startRowOffset][col + startColOffset];
        char end = wordSearch[row + endRowOffset][col + endColOffset];
        return (start == 'M' && end == 'S') || (start == 'S' && end == 'M');
    }
    // *** End Part 2 helper methods

    @Override
    public String part1(String input) {
        List<String> lines = Utils.splitLines(input);

        this.wordSearch = initializeWordSearch(lines);

        int rowSize = lines.size();
        int colSize = lines.getFirst().length();

        int count = 0;

        // Check rows
        for (int i = 0; i < rowSize; i++) {
            String row = new String(wordSearch[i]);
            count += countRegexOccurrencesForwardAndReverse(row, P1_REGEX_XMAS);
        }
        System.out.println("count: " + count);

        // Check columns
        for (int col = 0; col < colSize; col++) {
            StringBuilder column = new StringBuilder();
            for (int row = 0; row < rowSize; row++) {
                column.append(wordSearch[row][col]);
            }
            count += countRegexOccurrencesForwardAndReverse(column.toString(), P1_REGEX_XMAS);
        }
        System.out.println("count: " + count);

        // Check diagonals

        // Columns: start top-left, travel right in columns, capture diagonally right down
        int row = 0;
        int currentCol = 0;
        int col = currentCol;
        while (currentCol < colSize) {
            StringBuilder diagonal = new StringBuilder();
            while (row < rowSize && col < colSize) {
                diagonal.append(wordSearch[row][col]);
                row++;
                col++;
            }

            count += countRegexOccurrencesForwardAndReverse(diagonal.toString(), P1_REGEX_XMAS);

            row = 0;
            currentCol = currentCol + 1;
            col = currentCol;
        }

        // Rows: start top-left, travel down in rows, capture diagonally right down
        col = 0;
        int currentRow = 1;  // checked row 0 above
        row = currentRow;
        while (currentRow < rowSize) {
            StringBuilder diagonal = new StringBuilder();
            while (row < rowSize && col < colSize) {
                diagonal.append(wordSearch[row][col]);
                row++;
                col++;
            }

            count += countRegexOccurrencesForwardAndReverse(diagonal.toString(), P1_REGEX_XMAS);

            col = 0;
            currentRow = currentRow + 1;
            row = currentRow;
        }

        // Columns: start top-right, travel left in columns, capture diagonally left down
        row = 0;
        currentCol = colSize - 1;
        col = currentCol;
        while (currentCol >= 0) {
            StringBuilder diagonal = new StringBuilder();
            while (row < rowSize && col >= 0) {
                diagonal.append(wordSearch[row][col]);
                row++;
                col--;
            }

            count += countRegexOccurrencesForwardAndReverse(diagonal.toString(), P1_REGEX_XMAS);

            row = 0;
            currentCol = currentCol - 1;
            col = currentCol;
        }

        // Rows: start top-right, travel down in rows, capture diagonally left down
        col = colSize - 1;
        currentRow = 1;  // checked row 0 above
        row = currentRow;
        while (currentRow < rowSize) {
            StringBuilder diagonal = new StringBuilder();
            while (row < rowSize && col >= 0) {
                diagonal.append(wordSearch[row][col]);
                row++;
                col--;
            }

            count += countRegexOccurrencesForwardAndReverse(diagonal.toString(), P1_REGEX_XMAS);

            col = colSize - 1;
            currentRow = currentRow + 1;
            row = currentRow;
        }
        return Integer.toString(count);
    }

    @Override
    public String part2(String input) {
        List<String> lines = Utils.splitLines(input);

        this.wordSearch = initializeWordSearch(lines);

        int rowSize = lines.size();
        int colSize = lines.getFirst().length();

        int count = 0;

        // Avoid first row, last row & first col, last col as we check for "A" surrounded by "M" & "S"
        for (int row = 1; row < rowSize - 1; row++) {
            for (int col = 1; col < colSize - 1; col++) {
                if (isValidXPattern(row, col)) {
                    count++;
                }
            }
        }
        return Integer.toString(count);
    }
}
