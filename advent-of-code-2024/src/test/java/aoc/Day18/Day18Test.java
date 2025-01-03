package aoc.Day18;

import aoc.day18.Day18;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18Test {
    @Test
    public void testPart1Input1() {
        String input = "5,4\n" +
                "4,2\n" +
                "4,5\n" +
                "3,0\n" +
                "2,1\n" +
                "6,3\n" +
                "2,4\n" +
                "1,5\n" +
                "0,6\n" +
                "3,3\n" +
                "2,6\n" +
                "5,1\n" +
                "1,2\n" +
                "5,5\n" +
                "2,5\n" +
                "6,5\n" +
                "1,4\n" +
                "0,4\n" +
                "6,4\n" +
                "1,1\n" +
                "6,1\n" +
                "1,0\n" +
                "0,5\n" +
                "1,6\n" +
                "2,0";
        String result = new Day18().part1(input);
        String expected = "22";
        assertEquals(expected, result);
    }
    @Test
    public void testPart2Input1() {
        String input = "5,4\n" +
                "4,2\n" +
                "4,5\n" +
                "3,0\n" +
                "2,1\n" +
                "6,3\n" +
                "2,4\n" +
                "1,5\n" +
                "0,6\n" +
                "3,3\n" +
                "2,6\n" +
                "5,1\n" +
                "1,2\n" +
                "5,5\n" +
                "2,5\n" +
                "6,5\n" +
                "1,4\n" +
                "0,4\n" +
                "6,4\n" +
                "1,1\n" +
                "6,1\n" +
                "1,0\n" +
                "0,5\n" +
                "1,6\n" +
                "2,0";
        String result = new Day18().part2(input);
        String expected = "6,1";
        assertEquals(expected, result);
    }
}
