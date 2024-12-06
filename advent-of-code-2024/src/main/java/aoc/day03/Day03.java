package aoc.day03;

import aoc.Day;
import aoc.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 implements Day {
    public Integer multiplyInstructions(String line) {
        Pattern multiplyInstructionPattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
        Pattern numbersPattern = Pattern.compile("\\d{1,3}");
        int instructionSum = 0;

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

        return instructionSum;
    }

    @Override
    public String part1(String input) {
        List<String> lines = Utils.splitLines(input);

        int instructionSum = 0;

        for (String line : lines) {
            instructionSum += multiplyInstructions(line);
        }

        return Integer.toString(instructionSum);
    }

    @Override
    public String part2(String input) {
        // delete newline/cr from input to make regext parsing easier
        input = input.replaceAll("\\r\\n|\\n", "");

        // start regex - start of input up to first don't()
        Pattern startInstructionsPattern = Pattern.compile("^.*?don\\'t\\(\\)");
        // middle regex - include all between do() and don't()
        Pattern middleInstructionsPattern = Pattern.compile("do\\(\\).*?don\\'t\\(\\)");
        // end regex - do() until end of line with don't() as negative lookahead
        Pattern endInstructionsPattern = Pattern.compile("do\\(\\)(?!.*don't\\(\\)).*$");

        int instructionSum = 0;

        Matcher startInstructions = startInstructionsPattern.matcher(input);
        Matcher middleInstructions = middleInstructionsPattern.matcher(input);
        Matcher endInstructions = endInstructionsPattern.matcher(input);

        while (startInstructions.find()) {
            instructionSum += multiplyInstructions(startInstructions.group());
        }
        while (middleInstructions.find()) {
            instructionSum += multiplyInstructions(middleInstructions.group());
        }
        while (endInstructions.find()) {
            instructionSum += multiplyInstructions(endInstructions.group());
        }

        return Integer.toString(instructionSum);
    }
}
