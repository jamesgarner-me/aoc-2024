package aoc.day07;

import aoc.day01.Day01;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07Test {
    @Test
    public void testPart1() {
        String input = "190: 10 19\n" +
                "3267: 81 40 27\n" +
                "83: 17 5\n" +
                "156: 15 6\n" +
                "7290: 6 8 6 15\n" +
                "161011: 16 10 13\n" +
                "192: 17 8 14\n" +
                "21037: 9 7 18 13\n" +
                "292: 11 6 16 20";
        String result = new Day07().part1(input);
        String expected = "3749";
        assertEquals(expected, result);
    }

    @Test
    public void testPart2() {
        String input = "190: 10 19\n" +
                "3267: 81 40 27\n" +
                "83: 17 5\n" +
                "156: 15 6\n" +
                "7290: 6 8 6 15\n" +
                "161011: 16 10 13\n" +
                "192: 17 8 14\n" +
                "21037: 9 7 18 13\n" +
                "292: 11 6 16 20";
        String result = new Day07().part2(input);
        String expected = "11387";
        assertEquals(expected, result);
    }

}
