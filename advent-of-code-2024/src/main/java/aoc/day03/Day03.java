package aoc.day03;

import aoc.Day;
import aoc.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 implements Day {
    @Override
    public String part1(String input) {
        List<String> lines = Utils.splitLines(input);

        Pattern multiplyInstructionPattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
        Pattern numbersPattern = Pattern.compile("\\d{1,3}");

        int instructionSum = 0;

        for (String line : lines) {
            Matcher instructions = multiplyInstructionPattern.matcher(line);

            while (instructions.find()) {
                String instruction = instructions.group();
                Matcher numbers = numbersPattern.matcher(instruction);

                List<String> numbersToMultiply = new ArrayList<>();
                while (numbers.find()) {
                    numbersToMultiply.add(numbers.group());
                }
                instructionSum += Integer.parseInt(numbersToMultiply.get(0)) * Integer.parseInt(numbersToMultiply.get(1));
            }
        }
        return Integer.toString(instructionSum);
    }

    @Override
    public String part2(String input) {
        return input;
    }

}
