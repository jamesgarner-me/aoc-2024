package aoc.day02;

import aoc.Day;
import aoc.Utils;

import java.util.ArrayList;
import java.util.List;

public class Day02 implements Day {
    @Override
    public String part1(String input) {
        List<String> lines = Utils.splitLines(input);
        int LIMIT = 3;
        int safeReports = 0;
        for (String line : lines) {
            List<Integer> levels = new ArrayList<>();
            for (String level : line.split("\\s+")) {
                levels.add(Integer.parseInt(level));
            }
            // Ascending
            if (levels.get(1) > levels.get(0)) {
                int i = 0;
                while (i + 1 < levels.size() && levels.get(i + 1) > levels.get(i) && (levels.get(i + 1) - levels.get(i) <= LIMIT)) {
                    i++;
                }
                // Reached the end of the level
                if (i == levels.size() - 1) {
                    safeReports += 1;
                }
            }
            // Descending
            else if (levels.get(1) < levels.get(0)) {
                int i = 0;
                while (i + 1 < levels.size() && levels.get(i + 1) < levels.get(i) && (levels.get(i) - levels.get(i + 1) <= LIMIT)) {
                    i++;
                }
                // Reached the end of the level
                if (i == levels.size() - 1) {
                    safeReports += 1;
                }
            }
        }
        return Integer.toString(safeReports);
    }

    @Override
    public String part2(String input) {
        List<String> lines = Utils.splitLines(input);
        int LIMIT = 3;
        int safeReports = 0;
        for (String line : lines) {
            List<Integer> levels = new ArrayList<>();
            for (String level : line.split("\\s+")) {
                levels.add(Integer.parseInt(level));
            }

            int FAILURE_LIMIT = 1;
            // Ascending
            if (levels.get(1) > levels.get(0)) {
                int i = 0;
                int failureCount = 0;
                while (i + 1 < levels.size()) {
                    if (levels.get(i + 1) > levels.get(i) && (levels.get(i + 1) - levels.get(i) <= LIMIT)) {
                        i++;
                    } else {
                        failureCount++;
                        i++;
                    }
                }
                if (failureCount <= FAILURE_LIMIT) {
                    safeReports += 1;
                }
            }
            // Descending
            if (levels.get(1) < levels.get(0)) {
                int i = 0;
                int failureCount = 0;
                while (i + 1 < levels.size()) {
                    if (levels.get(i + 1) < levels.get(i) && (levels.get(i) - levels.get(i+1) <= LIMIT)) {
                        i++;
                    } else {
                        failureCount++;
                        i++;
                    }
                }
                if (failureCount <= FAILURE_LIMIT) {
                    safeReports += 1;
                }
            }
        }
        return Integer.toString(safeReports);
    }
}
