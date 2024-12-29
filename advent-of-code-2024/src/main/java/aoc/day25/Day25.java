package aoc.day25;

import aoc.Day;
import aoc.Utils;

import java.util.ArrayList;
import java.util.List;

public class Day25 implements Day {

    private ArrayList<Integer[]> locks = new ArrayList<>();
    private ArrayList<Integer[]> keys = new ArrayList<>();

    private void initialiseKeysAndLocks(String input) {
        List<String> lines = Utils.splitLines(input);

        // Start line i - each lock/key has 7 lines
        int i = 0;
        int lockCounter = 0;
        int keyCounter = 0;
        boolean isLock = false;

        for (String line : lines) {

            // Move top to bottom
            if (line.chars().allMatch(ch -> ch == '#')) {
                // If every char is '#' & on line 0 then it is a lock
                if (i == 0) {
                    // System.out.println("It's a lock!");
                    isLock = true;
                    locks.add(new Integer[5]);
                }
            }

            if (line.chars().allMatch(ch -> ch == '.')) {
                // If every char is '.' & on line 0 then it is a key
                if (i == 0) {
                    // System.out.println("It's a key!");
                    isLock = false;
                    keys.add(new Integer[5]);
                }
            }

            for (int j = 0; j < line.length(); j++) {
                if (isLock && line.charAt(j) == '#') {
                    locks.get(lockCounter)[j] = i;
                } else if (!isLock && line.charAt(j) == '.') {
                    keys.get(keyCounter)[j] = 5 - i;
                }
            }

            i++;

            if (line.length() < 5) {
                // In between locks and keys, reset
                i = 0;
                if (isLock) {
                    lockCounter += 1;
                } else {
                    keyCounter += 1;
                }
            }

        }

    }

    // Count when no overlaps in lock pin & key; if strict is `true` the key must be perfect fit
    private int checkKeysInLocks(boolean strict) {
        int keyFitCount = 0;
        int k = 0;

        for (int i = 0; i < locks.size(); i++) {
            for (int j = 0; j < keys.size(); j++) {
                while (k < 5) {
                    if (strict) {
                        if (locks.get(i)[k] + keys.get(j)[k] != 5) {
                            break;
                        }
                    } else {
                        // System.out.println("(k = " + k + ") " + locks.get(i)[k] + " + " + keys.get(j)[k] + "= " + (locks.get(i)[k] + keys.get(j)[k]));
                        if (locks.get(i)[k] + keys.get(j)[k] > 5) {
                            break;
                        }
                    }
                    k += 1;
                }
                if (k == 5) {
                    keyFitCount += 1;
                }
                k = 0;
            }
        }
        return keyFitCount;
    }

    @Override
    public String part1(String input) {
        initialiseKeysAndLocks(input);
        int count = checkKeysInLocks(false);
        return Integer.toString(count);
    }


    @Override
    public String part2(String input) {

        return "";

    }
}
