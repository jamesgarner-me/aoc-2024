package aoc.day07;

import aoc.Day;
import aoc.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day07 implements Day {

    private ArrayList<String[]> initialiseOperatorArray(int size, int base) {

        if (size == 1) return new ArrayList<>();

        size = size - 1;

        ArrayList<String[]> opsArray = new ArrayList<>();

        int[] baseCount = new int[size];
        int[] baseCountFlags = new int[size];

        Arrays.fill(baseCount, 0);
        Arrays.fill(baseCountFlags, 1);
        // 1 : +
        // 2 : *
        // 3 : ||

        // loop for every possible combination, e.g. when 4 numbers (3 operators): 2^3 = 8 (P1) or 3^3 = 9 (P2)
        for (int i = 0; i < Math.pow(base, size); i++) {

            // loop through 'size' times to generate each combination of operator
            String[] ops = new String[size];

            for (int j = 0; j < size; j++) {

                // assuming base of 2 (P1):
                //    based on its position calculate 2^j
                //    2^0 = 1, 2^1 = 2, 2^2 = 4, 2^3 = 8
                //    if the count is greater than this value for position,
                //    increment the flag (to invoke next operator) and reset count to 0
                if (baseCount[j] >= Math.pow(base, j)) {
                    baseCountFlags[j]++;

                    if (baseCountFlags[j] % (base + 1) == 0) {
                        baseCountFlags[j] = 1;
                    }

                    baseCount[j] = 0;
                }

                switch (baseCountFlags[j]) {
                    case 1 -> ops[j] = "+";
                    case 2 -> ops[j] = "*";
                    case 3 -> ops[j] = "||";
                }
                baseCount[j]++;

            }
            opsArray.add(ops);

        }
        return opsArray;

    }

    // who needs PEMDAS
    private long evaluate(String[] numbers, String[] ops) {

        if (numbers.length == 0) return -1;

        long result = Long.parseLong(numbers[0]);

        for (int i = 1; i < numbers.length; i++) {

            long num = Long.parseLong(numbers[i]);

            switch (ops[i - 1]) {
                case "+" -> result += num;
                case "*" -> result *= num;
                case "||" -> result = Long.parseLong(Long.toString(result) + Long.toString(num));
            }

        }
        return result;

    }

    private String runPart1or2Helper(String input, boolean isPartOne) {
        List<String> lines = Utils.splitLines(input);

        ArrayList<String[]> opsArray;

        long calibrationResult = 0;

        for (String line : lines) {

            String[] tokens = line.split(": ");
            long testValue = Long.parseLong(tokens[0]);
            String[] numbers = tokens[1].split(" ");

            if (isPartOne) {
                opsArray = initialiseOperatorArray(numbers.length, 2);
            } else {
                opsArray = initialiseOperatorArray(numbers.length, 3);
            }

            for (String[] ops : opsArray) {
                if (testValue == evaluate(numbers, ops)) {
                    calibrationResult += testValue;
                    break;
                }
            }
        }

        return Long.toString(calibrationResult);
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